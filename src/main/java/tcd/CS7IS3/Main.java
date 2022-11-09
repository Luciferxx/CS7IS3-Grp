package tcd.CS7IS3;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import tcd.CS7IS3.models.TopicModel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static String OUTPUT_FILE = "./output/results.txt";
    private static int MAX_RESULTS = 50;
    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<TopicModel> topics = loadTopics("topics");

        Analyzer analyzer = new StandardAnalyzer();
        Similarity similarity = new ClassicSimilarity();

        Directory indexDirectory = FSDirectory.open(Paths.get("./index"));
        IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        iwConfig.setSimilarity(similarity);
        IndexWriter indexWriter = new IndexWriter(indexDirectory, iwConfig);

        // TODO: @Luciferxx Build index using the dataparsers
        // TODO: Don't forget to include IDs
        // indexWriter.addDocuments();

        DirectoryReader ireader = DirectoryReader.open(indexDirectory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // TODO: @Luciferxx update fields
        /*
            Boost queries using the common words in the description and the narrative.
            Also boost queries using the title
         */
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"title", "author", "text"}, analyzer);

        File outputFile = new File(OUTPUT_FILE);
        PrintWriter writer = new PrintWriter(outputFile, StandardCharsets.UTF_8);
        for (TopicModel topic : topics) {
            String queryS = QueryParser.escape(topic.getDescription().trim());
            Query query = queryParser.parse(queryS);
            ScoreDoc[] hits = isearcher.search(query, MAX_RESULTS).scoreDocs;
            int i = 0;
            for (ScoreDoc hit : hits) {
                Document hitDoc = isearcher.doc(hit.doc);
                // query-id 0 document-id rank score STANDARD
                writer.println(topic.getNumber() + " 0 " + hitDoc.get("id") + " 0 " + hit.score + " STANDARD");
                i++;
            }
        }
        writer.close();
        ireader.close();
        indexDirectory.close();

        indexWriter.close();
    }

    private static ArrayList<TopicModel> loadTopics(String topicPath) throws IOException {
        List<String> fileData = Files.readAllLines(Paths.get(topicPath), StandardCharsets.UTF_8);
        ArrayList<TopicModel> topics = new ArrayList<TopicModel>();
        String fieldType = "";
        TopicModel topic = new TopicModel();
        StringBuilder readString = new StringBuilder();
        for(String line : fileData) {
            if(line.length() > 0) {
                if(line.charAt(0) == '<') {
                    String newFieldType = line.substring(1, line.indexOf('>'));
                    switch (newFieldType) {
                        case "top": topic = new TopicModel(); break;
                        case "/top": topics.add(topic); break;
                        case "num": topic.setNumber(Integer.parseInt(line.substring(14).trim())); break;
                        case "title": topic.setTitle(line.substring(8).trim());
                    }
                    if (!newFieldType.equals(fieldType)) {
                        switch (fieldType) {
                            case "desc": topic.setDescription(readString.toString().trim()); break;
                            case "narr": topic.setNarrative(readString.toString().trim()); break;
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