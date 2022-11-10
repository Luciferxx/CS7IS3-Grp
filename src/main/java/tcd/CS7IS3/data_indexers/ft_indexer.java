package tcd.CS7IS3.data_indexers;

import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import tcd.CS7IS3.data_parsers.ft;
import tcd.CS7IS3.models.FtModel;

public class ft_indexer {
	private static ArrayList<Document> ft_lucene_documents = new ArrayList<>();

	public ArrayList<Document> return_FR94_lucene_data() {
		ft data = new ft();
		ArrayList<FtModel> parsed_data;
		try {
			parsed_data = data.return_data();
			for (int i = 0; i < parsed_data.size(); i++)
				ft_lucene_documents.add(create_Document(parsed_data.get(i)));
		} catch (Exception e) {}

		return ft_lucene_documents;
	}

	private Document create_Document(FtModel ft_data) {
		Document doc = new Document();
		doc.add(new StringField("Id", ft_data.getDocno(), Field.Store.YES));
		doc.add(new TextField("Date", ft_data.getDate(), Field.Store.YES));
		doc.add(new TextField("Pub", ft_data.getPub(), Field.Store.YES));
		doc.add(new TextField("Page", ft_data.getPage(), Field.Store.YES));
		doc.add(new TextField("Text", ft_data.getText(), Field.Store.YES));
		doc.add(new TextField("Byline", ft_data.getByline(), Field.Store.YES));
		doc.add(new TextField("Dateline", ft_data.getDateline(), Field.Store.YES));
		doc.add(new TextField("Headline", ft_data.getHeadline(), Field.Store.YES));
		doc.add(new TextField("Profile", ft_data.getProfile(), Field.Store.YES));
		return doc;
	}
}
