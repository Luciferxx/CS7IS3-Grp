package tcd.CS7IS3.data_parsers;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

import tcd.CS7IS3.models.LATimesModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class latimes {
	private final static File LATIMES_DIR = new File("Data/latimes");

	private static ArrayList<LATimesModel> latimesDataList = new ArrayList<>();

	private static void parseFile(String parseDoc) throws IOException {
		LATimesModel latimesModel = null;
		File file = new File(parseDoc);
		Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
		for (Element e : doc.select("DOC")) {
			latimesModel = new LATimesModel();
			latimesModel.setDocno(e.select("DOCNO").text());
			latimesModel.setDocid(e.select("DOCID").text());
			latimesModel.setDate(e.select("DATE").text());
			latimesModel.setSection(e.select("SECTION").text());
			latimesModel.setLength(e.select("LENGTH").text());
			latimesModel.setHeadline(e.select("HEADLINE").text());
			latimesModel.setByline(e.select("BYLINE").text());
			latimesModel.setText(e.select("TEXT").text());
			latimesModel.setGraphic(e.select("GRAPHIC").text());
			latimesModel.setType(e.select("TYPE").text());
			latimesModel.setCorrectionDate(e.select("CORRECTION-DATE").text());
			latimesModel.setCorrection(e.select("CORRECTION").text());
			latimesDataList.add(latimesModel);
			// System.out.println("###############################################################");
			// System.out.println();

			// System.out.println("*************************************************************");
			// System.out.println(e.select("TEXT").text());
			// e.select("TEXT").text();
			// System.out.println("###############################################################");
		}
		// System.out.println(latimes.get(287).getDocno());
	}

	public static void parseAllFiles() throws IOException {
		File[] allFiles = LATIMES_DIR.listFiles();
		for (File file : allFiles) {
			if (!file.getName().contains("read"))
				// System.out.println();
				System.out.println(file.getName());
			parseFile(file.getAbsolutePath());
		}
	}

	public ArrayList<LATimesModel> getData() throws IOException {
		parseAllFiles();
		return latimesDataList;
	}

	public static void main(String Args[]) throws IOException {
		parseAllFiles();
		System.out.println(latimesDataList.size());
	}
}
