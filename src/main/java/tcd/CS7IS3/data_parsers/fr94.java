package tcd.CS7IS3.data_parsers;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

import tcd.CS7IS3.models.Fr94Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class fr94 {
	private static ArrayList<Fr94Model> fr94_list = new ArrayList<>();

	private static void parse_(String parse_doc) throws IOException {
		Fr94Model fr94_model = null;
		File file = new File(parse_doc);
		Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
		for (Element e : doc.select("DOC")) {
			fr94_model = new Fr94Model();
			fr94_model.setDocno(e.select("DOCNO").text());
			fr94_model.setDate(e.select("DATE").text());
			fr94_model.setText(e.select("TEXT}").text());
			fr94_model.setFr(e.select("FR}").text());
			fr94_model.setFootcite(e.select("FOOTCITE}").text());
			fr94_model.setCfrno(e.select("CFRNO}").text());
			fr94_model.setRindock(e.select("RINDOCK}").text());
			fr94_model.setUsDept(e.select("USDEPT}").text());
			fr94_model.setUsBureau(e.select("USBUREAU}").text());
			fr94_model.setImports(e.select("IMPORT}").text());
			fr94_model.setDoctile(e.select("DOCTITLE}").text());
			fr94_model.setAgency(e.select("AGENCY}").text());
			fr94_model.setAction(e.select("ACTION}").text());
			fr94_model.setSummary(e.select("SUMMARY}").text());
			fr94_model.setDate(e.select("DATE}").text());
			fr94_model.setAddress(e.select("ADDRESS}").text());
			fr94_model.setFurther(e.select("FURTHER}").text());
			fr94_model.setSupplem(e.select("SUPPLEM}").text());
			fr94_model.setSigner(e.select("SIGNER}").text());
			fr94_model.setSignjob(e.select("SIGNJOB}").text());
			fr94_model.setFrFiling(e.select("FRFILING}").text());
			fr94_model.setBilling(e.select("BILLING}").text());
			fr94_model.setFootnote(e.select("FOOTNOTE}").text());
			fr94_model.setFootname(e.select("FOOTNAME}").text());

			fr94_list.add(fr94_model);
			// System.out.println("###############################################################");
			// System.out.println();

			// System.out.println("*************************************************************");
			// System.out.println(e.select("TEXT").text());
			// e.select("TEXT").text();
			// System.out.println("###############################################################");
		}
		// System.out.println(latimes.get(287).getDocno());
	}

	public static void parse_all_ft_files() throws IOException {
		File dir = new File("Data/fr94");
		parseAllFiles(dir.getAbsolutePath());

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

	public static void main(String Args[]) throws IOException {
		parse_all_ft_files();
		System.out.println(fr94_list.size());
	}
}
