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

	private static ArrayList<FtModel> ft_list = new ArrayList<>();

	private static void parse_(String parse_doc) throws IOException {
		FtModel ft_model = null;
		File file = new File(parse_doc);
		Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
		for (Element e : doc.select("DOC")) {
			ft_model = new FtModel();
			ft_model.setDocno(e.select("DOCNO").text());
			ft_model.setDate(e.select("DATE").text());
			ft_model.setPub(e.select("PUB").text());
			ft_model.setPage(e.select("PAGE}").text());
			ft_model.setText(e.select("TEXT}").text());
			ft_model.setByline(e.select("BYLINE}").text());
			ft_model.setDateline(e.select("DATELINE}").text());
			ft_model.setHeadline(e.select("HEADLINE}").text());
			ft_model.setProfile(e.select("PROFILE}").text());
			ft_list.add(ft_model);
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
					if (!file.getName().equals("readchg.txt") && !file.getName().equals("readmefb.txt")
							&& !file.getName().contains("Zone.Identifier")) {
						parse_(file.getAbsolutePath());
					}
				}
			}
		}
	}

	public ArrayList<FtModel> return_data() throws IOException {
		parseAllFiles(FT_DIR.getAbsolutePath());
		return ft_list;
	}

	public static void main(String Args[]) throws IOException {
		parseAllFiles(FT_DIR.getAbsolutePath());
		System.out.println(ft_list.size());
	}
}
