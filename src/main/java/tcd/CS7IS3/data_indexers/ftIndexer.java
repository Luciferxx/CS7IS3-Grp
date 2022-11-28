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
        // Important
        doc.add(new StringField("id", ftData.getDocno(), Field.Store.YES));
        doc.add(new TextField("title", ftData.getHeadline(), Field.Store.YES));
        doc.add(new TextField("content", ftData.getText(), Field.Store.YES));
        doc.add(new TextField("date", ftData.getDate(), Field.Store.YES));

        // Important and Unique
        doc.add(new TextField("author", ftData.getByline(), Field.Store.YES));
        doc.add(new TextField("pub", ftData.getPub(), Field.Store.YES));

        // Not Important
        doc.add(new TextField("profile", ftData.getProfile(), Field.Store.YES));
        doc.add(new TextField("page", ftData.getPage(), Field.Store.YES));
        doc.add(new TextField("dateline", ftData.getDateline(), Field.Store.YES));

        return doc;
    }
}
