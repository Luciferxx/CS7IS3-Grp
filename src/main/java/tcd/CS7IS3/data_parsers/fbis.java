package tcd.CS7IS3.data_parsers;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

import tcd.CS7IS3.models.FbisModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class fbis {
private static ArrayList<FbisModel> fbis_data_list = new ArrayList<>();   
private static void  parse_(String parse_doc) throws IOException{
    FbisModel fbis_model  = null;
    File file = new File(parse_doc);
    Document doc =  Jsoup.parse(file, "UTF-8", "http://example.com/");
    for (Element e : doc.select("DOC"))
    {
        fbis_model = new FbisModel();
        fbis_model.setDocno(e.select("DOCNO").text());
        fbis_model.setDate1(e.select("DATE1").text());
        fbis_model.setHeader(e.select("HEADER").text());
        fbis_model.setFig(e.select("FIG").text());
        fbis_model.setF(e.select("F").text());
        fbis_model.setTxt5(e.select("TXT5").text());
        fbis_model.setText(e.select("TXT").text());
        fbis_data_list.add(fbis_model);
        // System.out.println("###############################################################");
        // System.out.println();
        
        // System.out.println("*************************************************************");
        // System.out.println(e.select("TEXT").text());
        // e.select("TEXT").text();
        // System.out.println("###############################################################");
    }
    // System.out.println(fbis_data_list.get(287).getDocno());
}
public static void parse_all_fbis_files() throws IOException
{
    File dir = new File("Data/fbis");
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
    parse_all_fbis_files();
    System.out.println(fbis_data_list.size());
}
}
