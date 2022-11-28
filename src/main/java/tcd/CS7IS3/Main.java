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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.search.highlight.*;

import tcd.CS7IS3.Utils.allDataIndexer;
import tcd.CS7IS3.models.TopicModel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.cli.*;

public class Main {

    private static String OUTPUT_DIR = "./output";
    private static String OUTPUT_FILE = "results.txt";
    private static int MAX_RESULTS = 50;

    public static void main(String[] args) throws IOException, ParseException, org.apache.commons.cli.ParseException {
        // Defaults for commandline arguments
        Analyzer analyzer = new EnglishAnalyzer();
        Similarity similarity = new ClassicSimilarity();

        // Setup command line options
        Options options = new Options();
        options.addOption("a", "analyzer", true, "Select Analyzer (standard, whitespace, simple, english)");
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
            System.out.println("Generated Index");
            allDataIndexer.generateIndex(analyzer, similarity);
        }

        ArrayList<TopicModel> topics = loadTopics("topics");

        DirectoryReader ireader = DirectoryReader.open(indexDirectory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        isearcher.setSimilarity(similarity);
        // TODO: @Luciferxx update fields
        /*
         * Boost queries using the common words in the description and the narrative.
         * Also boost queries using the title
         */

        HashMap<String, Float> boostMap = new HashMap<String, Float>();
        boostMap.put("Text", 5f);
        boostMap.put("Txt5", 2f);
        boostMap.put("Header", 7f);
        boostMap.put("Summary", 6f);
        boostMap.put("Action", 4f);
        boostMap.put("F", 3f);
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"Text", "Txt5", "Header", "Summary", "Action", "F"}, analyzer, boostMap);
        new File(OUTPUT_DIR).mkdirs();
        File outputFile = new File(OUTPUT_DIR, OUTPUT_FILE);
        PrintWriter writer = new PrintWriter(outputFile, StandardCharsets.UTF_8);
        for (TopicModel topic : topics) {
            String queryS = QueryParser.escape(topic.getTitle().trim());
            Query query = queryParser.parse(queryS);
            ScoreDoc[] hits = isearcher.search(query, MAX_RESULTS).scoreDocs;
            int i = 0;
            for (ScoreDoc hit : hits) {
                Document hitDoc = isearcher.doc(hit.doc);
                // query-id 0 document-id rank score STANDARD
                writer.println(topic.getNumber() + " 0 " + hitDoc.get("Id") + " 0 " + hit.score + " STANDARD");
                i++;
            }
        }
        writer.close();
        ireader.close();
        indexDirectory.close();
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