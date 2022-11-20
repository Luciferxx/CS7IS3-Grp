package tcd.CS7IS3.data_parsers;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Element;
import tcd.CS7IS3.models.FbisModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class fbis {
	private final static File FBIS_DIR = new File("Data/fbis");

	private static ArrayList<FbisModel> fbisDataList = new ArrayList<>();

	private static void parseFile(String parseDoc) throws IOException {
		FbisModel fbisModel = null;
		File file = new File(parseDoc);
		Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
		for (Element e : doc.select("DOC")) {
			fbisModel = new FbisModel();
			if(!e.select("DOCNO").text().contains("The datasets"))
			{
				fbisModel.setDocno(e.select("DOCNO").text());
			fbisModel.setDate1(e.select("DATE1").text());
			fbisModel.setHeader(e.select("HEADER").text());
			fbisModel.setFig(e.select("FIG").text());
			fbisModel.setF(e.select("F").text());
			fbisModel.setTxt5(e.select("TXT5").text());
			fbisModel.setText(e.select("TXT").text());	
			fbisDataList.add(fbisModel);
		}
			// System.out.println("###############################################################");
			// System.out.println();

			// System.out.println("*************************************************************");
			// System.out.println(e.select("TEXT").text());
			// e.select("TEXT").text();
			// System.out.println("###############################################################");
		}
		// System.out.println(fbis_data_list.get(287).getDocno());
	}

	public static void parseAllFiles(String path) throws IOException {
		File[] allFiles = new File(path).listFiles();
		for (File file : allFiles) {
			if (!file.getName().contains("read"))
				// System.out.println();
				System.out.println(file.getName());
				parseFile(file.getAbsolutePath());
		}
	}

	public ArrayList<FbisModel> getData() throws IOException {
		parseAllFiles(FBIS_DIR.getAbsolutePath());
		return fbisDataList;
	}

	public static void main(String Args[]) throws IOException {
		System.out.println(fbisDataList.size());
	}
}
