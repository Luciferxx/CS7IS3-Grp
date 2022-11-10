package tcd.CS7IS3.data_indexers;

import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import tcd.CS7IS3.data_parsers.ft;
import tcd.CS7IS3.models.FtModel;

public class ftIndexer {
	private static ArrayList<Document> ftDocuments = new ArrayList<>();

	public ArrayList<Document> getDocuments() {
		ft data = new ft();
		ArrayList<FtModel> parsedData;
		try {
			parsedData = data.getData();
			for (int i = 0; i < parsedData.size(); i++)
				ftDocuments.add(createDocument(parsedData.get(i)));
		} catch (Exception e) {
		}

		return ftDocuments;
	}

	private Document createDocument(FtModel ftData) {
		Document doc = new Document();
		doc.add(new StringField("Id", ftData.getDocno(), Field.Store.YES));
		doc.add(new TextField("Date", ftData.getDate(), Field.Store.YES));
		doc.add(new TextField("Pub", ftData.getPub(), Field.Store.YES));
		doc.add(new TextField("Page", ftData.getPage(), Field.Store.YES));
		doc.add(new TextField("Text", ftData.getText(), Field.Store.YES));
		doc.add(new TextField("Byline", ftData.getByline(), Field.Store.YES));
		doc.add(new TextField("Dateline", ftData.getDateline(), Field.Store.YES));
		doc.add(new TextField("Headline", ftData.getHeadline(), Field.Store.YES));
		doc.add(new TextField("Profile", ftData.getProfile(), Field.Store.YES));
		return doc;
	}
}
