package tcd.CS7IS3.data_parsers;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

import tcd.CS7IS3.models.Fr94Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class fr94 {
	private final static File FR94_DIR = new File("Data/fr94");

	private static ArrayList<Fr94Model> fr94DataList = new ArrayList<>();

	private static void parseFile(String parseDoc) throws IOException {
		Fr94Model fr94Model = null;
		File file = new File(parseDoc);
		Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
		for (Element e : doc.select("DOC")) {
			fr94Model = new Fr94Model();
			if(!e.select("DOCNO").text().contains("The datasets"))
			{fr94Model.setDocno(e.select("DOCNO").text());
			fr94Model.setDate(e.select("DATE").text());
			fr94Model.setText(e.select("TEXT").text());
			fr94Model.setFr(e.select("FR").text());
			fr94Model.setFootcite(e.select("FOOTCITE").text());
			fr94Model.setCfrno(e.select("CFRNO").text());
			fr94Model.setRindock(e.select("RINDOCK").text());
			fr94Model.setUsDept(e.select("USDEPT").text());
			fr94Model.setUsBureau(e.select("USBUREAU").text());
			fr94Model.setImports(e.select("IMPORT").text());
			fr94Model.setDoctile(e.select("DOCTITLE").text());
			fr94Model.setAgency(e.select("AGENCY").text());
			fr94Model.setAction(e.select("ACTION").text());
			fr94Model.setSummary(e.select("SUMMARY").text());
			fr94Model.setDate(e.select("DATE").text());
			fr94Model.setAddress(e.select("ADDRESS").text());
			fr94Model.setFurther(e.select("FURTHER").text());
			fr94Model.setSupplem(e.select("SUPPLEM").text());
			fr94Model.setSigner(e.select("SIGNER").text());
			fr94Model.setSignjob(e.select("SIGNJOB").text());
			fr94Model.setFrFiling(e.select("FRFILING").text());
			fr94Model.setBilling(e.select("BILLING").text());
			fr94Model.setFootnote(e.select("FOOTNOTE").text());
			fr94Model.setFootname(e.select("FOOTNAME").text());

			fr94DataList.add(fr94Model);}
			// System.out.println("###############################################################");
			// System.out.println();

			// System.out.println("*************** parse_all_ft_files();
			// .text());
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

	public  ArrayList<Fr94Model> getData() throws IOException {
		parseAllFiles(FR94_DIR.getAbsolutePath());
		return fr94DataList;
	}

	public static void main(String Args[]) throws IOException {
		// getData();
		System.out.println(fr94DataList.size());
	}
}
