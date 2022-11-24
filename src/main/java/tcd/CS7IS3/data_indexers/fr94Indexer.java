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
        doc.add(new StringField("Id", fr94Data.getDocno(), Field.Store.YES));
        doc.add(new TextField("Date", fr94Data.getDate(), Field.Store.YES));
        doc.add(new TextField("Text", fr94Data.getText(), Field.Store.YES));
        doc.add(new TextField("Fr", fr94Data.getFr(), Field.Store.YES));
        doc.add(new TextField("Footcite", fr94Data.getFootcite(), Field.Store.YES));
        doc.add(new TextField("Cfrno", fr94Data.getCfrno(), Field.Store.YES));
        doc.add(new TextField("Rindock", fr94Data.getRindock(), Field.Store.YES));
        doc.add(new TextField("UsDept", fr94Data.getUsDept(), Field.Store.YES));
        doc.add(new TextField("UsBureau", fr94Data.getUsBureau(), Field.Store.YES));
        doc.add(new TextField("Imports", fr94Data.getImports(), Field.Store.YES));
        doc.add(new TextField("Doctile", fr94Data.getDoctile(), Field.Store.YES));
        doc.add(new TextField("Agency", fr94Data.getAgency(), Field.Store.YES));
        doc.add(new TextField("Action", fr94Data.getAction(), Field.Store.YES));
        doc.add(new TextField("Summary", fr94Data.getSummary(), Field.Store.YES));
        doc.add(new TextField("Date", fr94Data.getDate(), Field.Store.YES));
        doc.add(new TextField("Address,", fr94Data.getAddress(), Field.Store.YES));
        doc.add(new TextField("Further", fr94Data.getFurther(), Field.Store.YES));
        doc.add(new TextField("Supplem", fr94Data.getSupplem(), Field.Store.YES));
        doc.add(new TextField("Signer", fr94Data.getSigner(), Field.Store.YES));
        doc.add(new TextField("Signjob", fr94Data.getSignjob(), Field.Store.YES));
        doc.add(new TextField("FrFiling", fr94Data.getFrFiling(), Field.Store.YES));
        doc.add(new TextField("Billing", fr94Data.getBilling(), Field.Store.YES));
        doc.add(new TextField("Footnote", fr94Data.getFootnote(), Field.Store.YES));
        doc.add(new TextField("Footname", fr94Data.getFootname(), Field.Store.YES));
        return doc;
    }
}
