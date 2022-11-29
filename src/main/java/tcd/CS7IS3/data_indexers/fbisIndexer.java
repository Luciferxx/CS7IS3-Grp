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
        // Important
        doc.add(new StringField("id", fbisData.getDocno(), Field.Store.YES));
        doc.add(new TextField("title", fbisData.getHeader(), Field.Store.YES));
        doc.add(new TextField("content", fbisData.getText(), Field.Store.YES));
        doc.add(new TextField("contentExtended", fbisData.getTxt5(), Field.Store.YES));
        doc.add(new TextField("date", fbisData.getDate1(), Field.Store.YES));

        // Important and Unique
        doc.add(new TextField("f", fbisData.getF(), Field.Store.YES)); // Seems to be subject?
        doc.add(new TextField("header", fbisData.getHeader(), Field.Store.YES));
        StringBuilder h = new StringBuilder();
        for (int i = 1; i <= 8; i ++ ) {
            String hString = fbisData.getH(i-1);
            if (!hString.isEmpty()) {
                h.append(hString).append(" ");
            }
        }
        doc.add(new TextField("h", h.toString(), Field.Store.YES));

        // Not Important
        doc.add(new TextField("fig", fbisData.getFig(), Field.Store.YES));

        String allContent = String.join(" ", fbisData.getText(), fbisData.getTxt5(), fbisData.getF(),
                fbisData.getHeader(), h.toString(), fbisData.getFig());
        doc.add(new TextField("allContent", allContent, Field.Store.YES));
        return doc;
    }
}
