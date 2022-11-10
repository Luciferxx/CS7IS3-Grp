package tcd.CS7IS3.Utils;

import java.util.ArrayList;

import org.apache.lucene.document.Document;

import tcd.CS7IS3.data_indexers.fbis_indexer;
import tcd.CS7IS3.data_indexers.fr94_indexer;

public class allDataIndexer {
    static ArrayList<Document> all_data_lucene_document = new ArrayList<>();
    static fbis_indexer fbis_in = new fbis_indexer();
    static fr94_indexer fr94_in = new fr94_indexer();
    //@Finn Please make the indexer class for the datasets that you are working on over here. 
    public static ArrayList<Document> data_indexer(){
        all_data_lucene_document.addAll(fbis_in.return_FBIS_lucene_data());
        all_data_lucene_document.addAll(fr94_in.return_FR94_lucene_data());
        //@Finn use addAll to add your lucene documents as per dataset to all_data_lucene_document. 
        return all_data_lucene_document;
    }
    public void Indexer()
    {
        //Our Indexer Writer
    }
    public static void main(String[] args)
    {
        data_indexer();
        System.out.println(all_data_lucene_document.size());
    }
}