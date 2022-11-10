package tcd.CS7IS3.Utils;

import java.util.ArrayList;

import org.apache.lucene.document.Document;

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

	public void Indexer() {
		// Our Indexer Writer
	}

	public static void main(String[] args) {
		dataIndexer();
		System.out.println(allDocuments.size());
	}
}
