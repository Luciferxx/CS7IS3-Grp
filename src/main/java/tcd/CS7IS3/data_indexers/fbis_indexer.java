package tcd.CS7IS3.data_indexers;

import java.util.ArrayList;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import tcd.CS7IS3.data_parsers.fbis;
import tcd.CS7IS3.models.FbisModel;
//@Finn refer to this class for creating lucene documents for other datasets as well.
public class fbis_indexer {
    private static ArrayList<Document> fbis_lucene_Documents = new ArrayList<>();
    
    public ArrayList<Document> return_FBIS_lucene_data()  {
        fbis data = new fbis();
        ArrayList<FbisModel> parsed_data;
        try
        {
            parsed_data = data.return_Fbis_Data(); 
            for (int i =0;i<parsed_data.size();i++)
            {
                fbis_lucene_Documents.add(create_Document(parsed_data.get(i)));
            }
        }
        catch(Exception e)
        {

        }
        
        return fbis_lucene_Documents;
    }
    private Document create_Document(FbisModel fbis_data){
        Document doc = new Document();
        doc.add(new StringField("Id",fbis_data.getDocno(),Field.Store.YES));
		doc.add(new TextField("Date",fbis_data.getDate1(),Field.Store.YES));
		doc.add(new TextField("Fig",fbis_data.getFig(),Field.Store.YES));
		doc.add(new TextField("F",fbis_data.getF(),Field.Store.YES));
		doc.add(new TextField("Text",fbis_data.getText(),Field.Store.YES));
        doc.add(new TextField("Txt5",fbis_data.getTxt5(),Field.Store.YES));
        doc.add(new TextField("Header",fbis_data.getHeader(),Field.Store.YES));
        return doc;
    }
    // public static void main(String[] args)
    // {
    //     return_FBIS_lucene_data();
    //     System.out.println(fbis_lucene_Documents.size());
    // }
}

