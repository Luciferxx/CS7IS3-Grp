package tcd.CS7IS3;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.queries.mlt.MoreLikeThisQuery;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tcd.CS7IS3.Utils.allDataIndexer;
import tcd.CS7IS3.Utils.customAnalyzer;
import tcd.CS7IS3.models.TopicModel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.BreakIterator;
import java.util.*;

import org.apache.commons.cli.*;

public class Main {

    private static String OUTPUT_DIR = "./output";
    private static String OUTPUT_FILE = "results.txt";
    private static int MAX_RESULTS = 1000;
    public static String STOPWORD_PATH = "./stopwords.txt";

    public static void main(String[] args) throws IOException, ParseException, org.apache.commons.cli.ParseException {
        // Defaults for commandline arguments
        Analyzer analyzer = new EnglishAnalyzer();
        Similarity similarity = new BM25Similarity();

        // Setup command line options
        Options options = new Options();
        options.addOption("a", "analyzer", true, "Select Analyzer (standard, whitespace, simple, english, custom)");
        options.addOption("s", "similarities", true, "Select Similarities (classic, bm25, boolean)");
        options.addOption("i", "index", false, "Generates index");
        options.addOption("h", "help", false, "Help");

        // Parse commandline arguments
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if(cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("CS7IS3-Grp", options);
            return;
        }
        if(cmd.hasOption("a")) {
            switch (cmd.getOptionValue("a").toLowerCase()) {
                case "standard": analyzer = new StandardAnalyzer(); break;
                case "whitespace": analyzer = new WhitespaceAnalyzer(); break;
                case "simple": analyzer = new SimpleAnalyzer(); break;
                case "english": analyzer = new EnglishAnalyzer(); break;
                case "custom": analyzer = new customAnalyzer(); break;
            }
        }
        if(cmd.hasOption("s")) {
            switch (cmd.getOptionValue("s").toLowerCase()) {
                case "classic": similarity = new ClassicSimilarity(); break;
                case "bm25": similarity = new BM25Similarity(); break;
                case "boolean": similarity = new BooleanSimilarity(); break;
                case "lmdirichlet": similarity = new LMDirichletSimilarity(); break;
            }
        }
        Directory indexDirectory = FSDirectory.open(Paths.get(LuceneContstants.INDEX_LOC));
        if(cmd.hasOption("i") || !DirectoryReader.indexExists(indexDirectory)) {
            System.out.println("Generating index...");
            allDataIndexer.generateIndex(analyzer, similarity);
        }

        System.out.println("Loading Topics");
        ArrayList<TopicModel> topics = loadTopics("topics");

        DirectoryReader ireader = DirectoryReader.open(indexDirectory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        isearcher.setSimilarity(similarity);

        // http://www.lextek.com/manuals/onix/stopwords1.html
        // https://gist.github.com/larsyencken/1440509
        List<String> stopwords = Files.readAllLines(Path.of(STOPWORD_PATH));

        HashMap<String, Float> boostMap = new HashMap<String, Float>();
        boostMap.put("title", 0.08f);
        boostMap.put("allContent", 0.92f);

        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"title", "allContent"}, analyzer, boostMap);
        new File(OUTPUT_DIR).mkdirs();
        File outputFile = new File(OUTPUT_DIR, OUTPUT_FILE);
        PrintWriter writer = new PrintWriter(outputFile, StandardCharsets.UTF_8);
        System.out.println("Running Queries");
        for (TopicModel topic : topics) {
            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

            Query titleQuery = queryParser.parse(QueryParser.escape(topic.getTitle().trim()));
            booleanQuery.add(new BoostQuery(titleQuery, 5f), BooleanClause.Occur.SHOULD);

            Query descriptionQuery = queryParser.parse(QueryParser.escape(removeStopWords(topic.getDescription().trim(), stopwords)));
            booleanQuery.add(new BoostQuery(descriptionQuery, 4f), BooleanClause.Occur.SHOULD);

            // Can contain multiple relevant or irrelevant statements
            BreakIterator iterator = BreakIterator.getSentenceInstance();
            String narrative = topic.getNarrative().trim();
            iterator.setText(narrative);
            int index = 0;
            while (iterator.next() != BreakIterator.DONE) {
                String sentence = narrative.substring(index, iterator.current());
                if (sentence.length() > 0) {
                    sentence = removeStopWords(sentence, stopwords);
                    Query narrativeQuery = queryParser.parse(QueryParser.escape(sentence));
                    if (!sentence.contains("not relevant") && !sentence.contains("irrelevant")) {
                        booleanQuery.add(new BoostQuery(narrativeQuery, 1.4f), BooleanClause.Occur.SHOULD);
                    } else {
                        booleanQuery.add(new BoostQuery(narrativeQuery, 2f), BooleanClause.Occur.FILTER);
                    }
                }
                index = iterator.current();
            }

            ArrayList<Query> expandedQueries = expandQuery(isearcher, analyzer, ireader, booleanQuery.build());

            for (Query expandedQuery : expandedQueries) {
                booleanQuery.add(expandedQuery, BooleanClause.Occur.SHOULD);
            }

            ScoreDoc[] hits = isearcher.search(booleanQuery.build(), MAX_RESULTS).scoreDocs;
            for (ScoreDoc hit : hits) {
                Document hitDoc = isearcher.doc(hit.doc);
                // query-id 0 document-id rank score STANDARD
                writer.println(topic.getNumber() + " 0 " + hitDoc.get("id") + " 0 " + hit.score + " STANDARD");
            }
        }

        System.out.println("Saving Results");
        writer.close();
        ireader.close();
        indexDirectory.close();
    }

    private static ArrayList<Query> expandQuery(IndexSearcher isearcher, Analyzer analyzer, DirectoryReader ireader,
                                                Query query) throws IOException {
        ArrayList<Query> expandedQueries = new ArrayList<>();
        ScoreDoc[] hits = isearcher.search(query, 12).scoreDocs;
        for (ScoreDoc hit : hits) {
            Document hitDoc = ireader.document(hit.doc);
            String field = hitDoc.getField("allContent").stringValue();
            MoreLikeThisQuery mltqExpanded = new MoreLikeThisQuery(field, new String[]{"allContent"}, analyzer, "allContent");
            Query expandedQuery = mltqExpanded.rewrite(ireader);
            expandedQueries.add(expandedQuery);
        }
        return expandedQueries;
    }

    private static String removeStopWords(String s, List<String> stopwords) {
        ArrayList<String> allWords = Stream.of(s.toLowerCase().split(" ")).collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(stopwords);
        return String.join(" ", allWords);
    }

    private static ArrayList<TopicModel> loadTopics(String topicPath) throws IOException {
        List<String> fileData = Files.readAllLines(Paths.get(topicPath), StandardCharsets.UTF_8);
        ArrayList<TopicModel> topics = new ArrayList<TopicModel>();
        String fieldType = "";
        TopicModel topic = new TopicModel();
        StringBuilder readString = new StringBuilder();
        for (String line : fileData) {
            if (line.length() > 0) {
                if (line.charAt(0) == '<') {
                    String newFieldType = line.substring(1, line.indexOf('>'));
                    switch (newFieldType) {
                        case "top":
                            topic = new TopicModel();
                            break;
                        case "/top":
                            topics.add(topic);
                            break;
                        case "num":
                            topic.setNumber(Integer.parseInt(line.substring(14).trim()));
                            break;
                        case "title":
                            topic.setTitle(line.substring(8).trim());
                    }
                    if (!newFieldType.equals(fieldType)) {
                        switch (fieldType) {
                            case "desc":
                                topic.setDescription(readString.toString().trim());
                                break;
                            case "narr":
                                topic.setNarrative(readString.toString().trim());
                                break;
                        }
                        readString = new StringBuilder();
                    }
                    fieldType = newFieldType;
                } else {
                    readString.append(line.trim()).append(" ");
                }
            }
        }
        return topics;
    }
}