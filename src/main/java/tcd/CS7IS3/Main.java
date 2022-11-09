package tcd.CS7IS3;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import tcd.CS7IS3.models.TopicModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<TopicModel> topics = loadTopics("topics");

        Analyzer analyzer = new StandardAnalyzer();
        Similarity similarity = new ClassicSimilarity();

        Directory indexDirectory = FSDirectory.open(Paths.get("./index"));
        IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        iwConfig.setSimilarity(similarity);
        IndexWriter indexWriter = new IndexWriter(indexDirectory, iwConfig);

        // TODO: @Luciferxx Build index using the dataparsers
        // indexWriter.addDocuments();

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

    private static ArrayList<String> createDocuments(ArrayList<TopicModel> topics) {
        ArrayList<String> queries = new ArrayList<>();
        for(TopicModel topic : topics) {
            // Do Something
        }
        return queries;
    }
}