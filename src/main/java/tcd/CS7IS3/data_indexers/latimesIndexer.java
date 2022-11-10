package tcd.CS7IS3.data_indexers;

import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import tcd.CS7IS3.data_parsers.latimes;
import tcd.CS7IS3.models.LATimesModel;

public class latimesIndexer {
	private static ArrayList<Document> latimesDocuments = new ArrayList<>();

	public ArrayList<Document> getDocuments() {
		latimes data = new latimes();
		ArrayList<LATimesModel> parsedData;
		try {
			parsedData = data.getData();
			for (int i = 0; i < parsedData.size(); i++)
				latimesDocuments.add(createDocument(parsedData.get(i)));
		} catch (Exception e) {
		}

		return latimesDocuments;
	}

	private Document createDocument(LATimesModel latimesData) {
		Document doc = new Document();
		doc.add(new StringField("Id", latimesData.getDocno(), Field.Store.YES));
		doc.add(new TextField("DocID", latimesData.getDocid(), Field.Store.YES));
		doc.add(new TextField("Date", latimesData.getDate(), Field.Store.YES));
		doc.add(new TextField("Section", latimesData.getSection(), Field.Store.YES));
		doc.add(new TextField("Length", latimesData.getLength(), Field.Store.YES));
		doc.add(new TextField("Headline", latimesData.getHeadline(), Field.Store.YES));
		doc.add(new TextField("Byline", latimesData.getByline(), Field.Store.YES));
		doc.add(new TextField("Text", latimesData.getText(), Field.Store.YES));
		doc.add(new TextField("Graphic", latimesData.getGraphic(), Field.Store.YES));
		doc.add(new TextField("Type", latimesData.getType(), Field.Store.YES));
		doc.add(new TextField("CorrectionDate", latimesData.getCorrectionDate(), Field.Store.YES));
		doc.add(new TextField("Correction", latimesData.getCorrection(), Field.Store.YES));
		return doc;
	}
}
