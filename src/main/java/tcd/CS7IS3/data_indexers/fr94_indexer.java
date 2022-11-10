package tcd.CS7IS3.data_indexers;

import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import tcd.CS7IS3.data_parsers.fr94;
import tcd.CS7IS3.models.Fr94Model;

public class fr94_indexer {
	private static ArrayList<Document> fr94_lucene_documents = new ArrayList<>();

	public ArrayList<Document> return_FR94_lucene_data() {
		fr94 data = new fr94();
		ArrayList<Fr94Model> parsed_data;
		try {
			parsed_data = data.return_data();
			for (int i = 0; i < parsed_data.size(); i++)
				fr94_lucene_documents.add(create_Document(parsed_data.get(i)));
		} catch (Exception e) {}

		return fr94_lucene_documents;
	}

	private Document create_Document(Fr94Model fr94_data) {
		Document doc = new Document();
		doc.add(new StringField("Id", fr94_data.getDocno(), Field.Store.YES));
		doc.add(new TextField("Date", fr94_data.getDate(), Field.Store.YES));
		doc.add(new TextField("Text", fr94_data.getText(), Field.Store.YES));
		doc.add(new TextField("Fr", fr94_data.getFr(), Field.Store.YES));
		doc.add(new TextField("Footcite", fr94_data.getFootcite(), Field.Store.YES));
		doc.add(new TextField("Cfrno", fr94_data.getCfrno(), Field.Store.YES));
		doc.add(new TextField("Rindock", fr94_data.getRindock(), Field.Store.YES));
		doc.add(new TextField("UsDept", fr94_data.getUsDept(), Field.Store.YES));
		doc.add(new TextField("UsBureau", fr94_data.getUsBureau(), Field.Store.YES));
		doc.add(new TextField("Imports", fr94_data.getImports(), Field.Store.YES));
		doc.add(new TextField("Doctile", fr94_data.getDoctile(), Field.Store.YES));
		doc.add(new TextField("Agency", fr94_data.getAgency(), Field.Store.YES));
		doc.add(new TextField("Action", fr94_data.getAction(), Field.Store.YES));
		doc.add(new TextField("Summary", fr94_data.getSummary(), Field.Store.YES));
		doc.add(new TextField("Date", fr94_data.getDate(), Field.Store.YES));
		doc.add(new TextField("Address,", fr94_data.getAddress(), Field.Store.YES));
		doc.add(new TextField("Further", fr94_data.getFurther(), Field.Store.YES));
		doc.add(new TextField("Supplem", fr94_data.getSupplem(), Field.Store.YES));
		doc.add(new TextField("Signer", fr94_data.getSigner(), Field.Store.YES));
		doc.add(new TextField("Signjob", fr94_data.getSignjob(), Field.Store.YES));
		doc.add(new TextField("FrFiling", fr94_data.getFrFiling(), Field.Store.YES));
		doc.add(new TextField("Billing", fr94_data.getBilling(), Field.Store.YES));
		doc.add(new TextField("Footnote", fr94_data.getFootnote(), Field.Store.YES));
		doc.add(new TextField("Footname", fr94_data.getFootname(), Field.Store.YES));
		return doc;
	}
	// public static void main(String[] args)
	// {
	// return_FBIS_lucene_data();
	// System.out.println(fbis_lucene_Documents.size());
	// }
}
