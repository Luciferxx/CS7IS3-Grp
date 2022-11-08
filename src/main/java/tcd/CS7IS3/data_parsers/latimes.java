package tcd.CS7IS3.data_parsers;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

import tcd.CS7IS3.models.LATimesModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class latimes {
private static ArrayList<LATimesModel> latime_list = new ArrayList<>();   
private static void  parse_(String parse_doc) throws IOException{
    LATimesModel latimes_model  = null;
    File file = new File(parse_doc);
    Document doc =  Jsoup.parse(file, "UTF-8", "http://example.com/");
    for (Element e : doc.select("DOC"))
    {
        latimes_model = new LATimesModel();
        latimes_model.setDocno(e.select("DOCNO").text());
        latimes_model.setDocid(e.select("DOCID").text());
        latimes_model.setDate(e.select("DATE").text());
        latimes_model.setSection(e.select("SECTION").text());
        latimes_model.setLength(e.select("LENGTH").text());
        latimes_model.setHeadline(e.select("HEADLINE").text());
        latimes_model.setByline(e.select("BYLINE").text());
        latimes_model.setText(e.select("TEXT").text());
        latimes_model.setGraphic(e.select("GRAPHIC").text());
        latimes_model.setType(e.select("TYPE").text());
        latimes_model.setCorrectionDate(e.select("CORRECTION-DATE").text());
        latimes_model.setCorrection(e.select("CORRECTION").text());
        latime_list.add(latimes_model);
        // System.out.println("###############################################################");
        // System.out.println();
        
        // System.out.println("*************************************************************");
        // System.out.println(e.select("TEXT").text());
        // e.select("TEXT").text();
        // System.out.println("###############################################################");
    }
    // System.out.println(latimes.get(287).getDocno());
}
public static void parse_all_latimes_files() throws IOException
{
    File dir = new File("Data/latimes");
    File[] all_files = dir.listFiles();
    for (File file : all_files)
    {   
        if(!file.getName().equals("readchg.txt") && !file.getName().equals("readmefb.txt") )
            // System.out.println();
            System.out.println(file.getName());
            parse_(file.getAbsolutePath());
    }

}    
public static void main(String Args[]) throws IOException
{
    parse_all_latimes_files();
    System.out.println(latime_list.size());
}
}
