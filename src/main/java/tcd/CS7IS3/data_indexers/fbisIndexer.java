package tcd.CS7IS3.data_indexers;

import java.util.ArrayList;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import tcd.CS7IS3.data_parsers.fbis;
import tcd.CS7IS3.models.FbisModel;

//@Finn refer to this class for creating lucene documents for other datasets as well.
public class fbisIndexer {
    private static ArrayList<Document> fbisDocuments = new ArrayList<>();

    public ArrayList<Document> getDocuments() {
        fbis data = new fbis();
        ArrayList<FbisModel> parsedData;
        try {
            parsedData = data.getData();
            for (int i = 0; i < parsedData.size(); i++) {
                fbisDocuments.add(createDocument(parsedData.get(i)));
            }
        } catch (Exception e) {
        }

        return fbisDocuments;
    }

    private Document createDocument(FbisModel fbisData) {
        Document doc = new Document();
        doc.add(new StringField("Id", fbisData.getDocno(), Field.Store.YES));
        doc.add(new TextField("Date", fbisData.getDate1(), Field.Store.YES));
        doc.add(new TextField("Fig", fbisData.getFig(), Field.Store.YES));
        doc.add(new TextField("F", fbisData.getF(), Field.Store.YES));
        doc.add(new TextField("Text", fbisData.getText(), Field.Store.YES));
        doc.add(new TextField("Txt5", fbisData.getTxt5(), Field.Store.YES));
        doc.add(new TextField("Header", fbisData.getHeader(), Field.Store.YES));
        return doc;
    }
}
