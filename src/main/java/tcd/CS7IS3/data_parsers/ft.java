package tcd.CS7IS3.data_parsers;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

import tcd.CS7IS3.models.FtModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ft {
	private final static File FT_DIR = new File("Data/ft");

	private static ArrayList<FtModel> ftDataList = new ArrayList<>();

	private static void parseFile(String parseDoc) throws IOException {
		FtModel ftModel = null;
		File file = new File(parseDoc);
		Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
		for (Element e : doc.select("DOC")) {
			ftModel = new FtModel();
			ftModel.setDocno(e.select("DOCNO").text());
			ftModel.setDate(e.select("DATE").text());
			ftModel.setPub(e.select("PUB").text());
			ftModel.setPage(e.select("PAGE").text());
			ftModel.setText(e.select("TEXT").text());
			ftModel.setByline(e.select("BYLINE").text());
			ftModel.setDateline(e.select("DATELINE").text());
			ftModel.setHeadline(e.select("HEADLINE").text());
			ftModel.setProfile(e.select("PROFILE").text());
			ftDataList.add(ftModel);
			// System.out.println("###############################################################");
			// System.out.println();

			// System.out.println("*************************************************************");
			// System.out.println(e.select("TEXT").text());
			// e.select("TEXT").text();
			// System.out.println("###############################################################");
		}
		// System.out.println(latimes.get(287).getDocno());
	}

	public static void parseAllFiles(String path) throws IOException {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list != null) {
			for (File file : list) {
				if (file.isDirectory()) {
					parseAllFiles(file.getAbsolutePath());
				} else {
					if (!file.getName().contains("read")
							&& !file.getName().contains("Zone.Identifier")) {
						parseFile(file.getAbsolutePath());
					}
				}
			}
		}
	}

	public ArrayList<FtModel> getData() throws IOException {
		parseAllFiles(FT_DIR.getAbsolutePath());
		return ftDataList;
	}

	public static void main(String Args[]) throws IOException {
		parseAllFiles(FT_DIR.getAbsolutePath());
		System.out.println(ftDataList.size());
	}
}
