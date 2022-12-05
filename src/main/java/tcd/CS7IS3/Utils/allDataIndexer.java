package tcd.CS7IS3.Utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import tcd.CS7IS3.LuceneContstants;
import tcd.CS7IS3.data_indexers.fbisIndexer;
import tcd.CS7IS3.data_indexers.fr94Indexer;
import tcd.CS7IS3.data_indexers.ftIndexer;
import tcd.CS7IS3.data_indexers.latimesIndexer;

public class allDataIndexer {
    static ArrayList<Document> allDocuments = new ArrayList<>();
    static fbisIndexer fbisIn = new fbisIndexer();
    static fr94Indexer fr94In = new fr94Indexer();
    static ftIndexer ftIn = new ftIndexer();
    static latimesIndexer latimesIn = new latimesIndexer();

    public static ArrayList<Document> dataIndexer() {
        allDocuments.addAll(fbisIn.getDocuments());
        allDocuments.addAll(fr94In.getDocuments());
        allDocuments.addAll(ftIn.getDocuments());
        allDocuments.addAll(latimesIn.getDocuments());
        return allDocuments;
    }

    public static void Indexer(Analyzer analyzer, Similarity similarity) throws IOException {
        dataIndexer();
        Directory directory;
        directory = FSDirectory.open(Paths.get(LuceneContstants.INDEX_LOC));
        // Configure the index writer
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        // use the similarity_chosen
        iwc.setSimilarity(similarity);

        IndexWriter indexWriter = new IndexWriter(directory, iwc);
        //Add the documents parsed from the dataset into the indexWriter
        indexWriter.addDocuments(allDocuments);
        indexWriter.close();
        System.out.println(String.valueOf(allDocuments.size()) + " documents have been indexed \nIndexing complete");
    }

    public static void generateIndex(Analyzer analyzer, Similarity similarity) throws IOException {
        Indexer(analyzer, similarity);
    }
}
