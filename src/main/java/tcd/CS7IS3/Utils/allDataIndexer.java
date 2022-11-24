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
    static AnalyzerChoice analyzer_choice = new AnalyzerChoice();
    static SimilarityChoice similarity_choice = new SimilarityChoice();

    // @Finn Please make the indexer class for the datasets that you are working on
    // over here.
    public static ArrayList<Document> dataIndexer() {
        allDocuments.addAll(fbisIn.getDocuments());
        allDocuments.addAll(fr94In.getDocuments());
        allDocuments.addAll(ftIn.getDocuments());
        allDocuments.addAll(latimesIn.getDocuments());
        // @Finn use addAll to add your lucene documents as per dataset to
        // all_data_lucene_document.
        return allDocuments;
    }

    public static void Indexer(String choice_analyzer, String choice_similarity) throws IOException {
        dataIndexer();
        Directory directory;
        Analyzer analyzer_chosen = analyzer_choice.Input_Choice(choice_analyzer);
        Similarity similarity_chosen = similarity_choice.Input_Choice(choice_similarity);
        directory = FSDirectory.open(Paths.get(LuceneContstants.INDEX_LOC));
        //Configure the index writer
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer_chosen);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        //use the similarity_chosen
        iwc.setSimilarity(similarity_chosen);

        IndexWriter indexWriter = new IndexWriter(directory, iwc);
        //Add the documents parsed from the dataset into the indexWriter
        indexWriter.addDocuments(allDocuments);
        indexWriter.close();
        System.out.println(String.valueOf(allDocuments.size()) + " documents have been indexed \n Indexing complete");
    }

    public static void main(String[] args) throws IOException {
        Indexer(LuceneContstants.chosen_Analyzer, LuceneContstants.chosen_Similarity);
        System.out.println(allDocuments.size());
    }
}
