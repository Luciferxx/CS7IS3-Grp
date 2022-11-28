package tcd.CS7IS3.data_indexers;

import java.util.ArrayList;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import tcd.CS7IS3.data_parsers.fr94;
import tcd.CS7IS3.models.Fr94Model;

public class fr94Indexer {
    private static ArrayList<Document> fr94Documents = new ArrayList<>();

    public ArrayList<Document> getDocuments() {
        fr94 data = new fr94();
        ArrayList<Fr94Model> parsedData;
        try {
            parsedData = data.getData();
            for (int i = 0; i < parsedData.size(); i++)
                fr94Documents.add(createDocument(parsedData.get(i)));
        } catch (Exception e) {
        }
        return fr94Documents;
    }

    private Document createDocument(Fr94Model fr94Data) {
        Document doc = new Document();
        // Important
        doc.add(new StringField("id", fr94Data.getDocno(), Field.Store.YES));
        doc.add(new TextField("title", fr94Data.getDoctitle(), Field.Store.YES));
        doc.add(new TextField("content", fr94Data.getText(), Field.Store.YES));
        doc.add(new TextField("contentExtended", fr94Data.getSupplem(), Field.Store.YES));
        doc.add(new TextField("date", fr94Data.getDate(), Field.Store.YES));

        // Important and Unique
        doc.add(new TextField("summary", fr94Data.getSummary(), Field.Store.YES));
        doc.add(new TextField("keyWords", fr94Data.getAction(), Field.Store.YES));
        doc.add(new TextField("usBureau", fr94Data.getUsBureau(), Field.Store.YES));
        doc.add(new TextField("usDep", fr94Data.getUsDept(), Field.Store.YES));
        doc.add(new TextField("agency", fr94Data.getAgency(), Field.Store.YES));
        doc.add(new TextField("address,", fr94Data.getAddress(), Field.Store.YES));
        doc.add(new TextField("furtherLinks", fr94Data.getFurther(), Field.Store.YES));
        doc.add(new TextField("signer", fr94Data.getSigner(), Field.Store.YES));
        doc.add(new TextField("signjob", fr94Data.getSignjob(), Field.Store.YES));
        doc.add(new TextField("footnote", fr94Data.getFootnote(), Field.Store.YES));
        doc.add(new TextField("footname", fr94Data.getFootname(), Field.Store.YES));

        // Not Important
        doc.add(new TextField("fr", fr94Data.getFr(), Field.Store.YES));
        doc.add(new TextField("footcite", fr94Data.getFootcite(), Field.Store.YES));
        doc.add(new TextField("cfrno", fr94Data.getCfrno(), Field.Store.YES));
        doc.add(new TextField("rindock", fr94Data.getRindock(), Field.Store.YES));
        doc.add(new TextField("imports", fr94Data.getImports(), Field.Store.YES));
        doc.add(new TextField("billing", fr94Data.getBilling(), Field.Store.YES));
        doc.add(new TextField("frFiling", fr94Data.getFrFiling(), Field.Store.YES));

        return doc;
    }
}
