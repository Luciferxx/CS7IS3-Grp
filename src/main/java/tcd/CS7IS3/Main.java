package tcd.CS7IS3;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import tcd.CS7IS3.Utils.AnalyzerChoice;
import tcd.CS7IS3.Utils.SimilarityChoice;
import tcd.CS7IS3.Utils.allDataIndexer;
import tcd.CS7IS3.models.TopicModel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
	private static String OUTPUT_FILE = "./output/results.txt";
	private static int MAX_RESULTS = 50;
	static AnalyzerChoice analyzer_choice = new AnalyzerChoice();
	static SimilarityChoice similarity_choice = new SimilarityChoice();
	public static void main(String[] args) throws IOException, ParseException {
		ArrayList<TopicModel> topics = loadTopics("topics");

		Analyzer analyzer = new StandardAnalyzer();
		Similarity similarity = new ClassicSimilarity();
		Directory indexDirectory = FSDirectory.open(Paths.get(LuceneContstants.INDEX_LOC));
		// Directory directory = FSDirectory.open(Paths.get(LuceneConstants.INDEX_LOC));
		// DirectoryReader dir_Reader = DirectoryReader.open(directory);
		// IndexSearcher search_indexer = new IndexSearcher(dir_Reader);
		// search_indexer.setSimilarity(similarity_chosen);
		
		// IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
		// iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		// iwConfig.setSimilarity(similarity);
		// IndexWriter indexWriter = new IndexWriter(indexDirectory, iwConfig);
		// allDataIndexer dataIndexer = new allDataIndexer(); 
		// indexWriter.addDocuments(allDataIndexer.dataIndexer());
		
		DirectoryReader ireader = DirectoryReader.open(indexDirectory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		// TODO: @Luciferxx update fields
		/*
		 * Boost queries using the common words in the description and the narrative.
		 * Also boost queries using the title
		 */
		// doc.add(new TextField("Date", fbisData.getDate1(), Field.Store.YES));
		// doc.add(new TextField("Fig", fbisData.getFig(), Field.Store.YES));
		// doc.add(new TextField("F", fbisData.getF(), Field.Store.YES));
		// doc.add(new TextField("Text", fbisData.getText(), Field.Store.YES));
		// doc.add(new TextField("Txt5", fbisData.getTxt5(), Field.Store.YES));
		// doc.add(new TextField("Header", fbisData.getHeader(), Field.Store.YES));
		// doc.add(new TextField("Date", fr94Data.getDate(), Field.Store.YES));
		// doc.add(new TextField("Text", fr94Data.getText(), Field.Store.YES));
		// doc.add(new TextField("Fr", fr94Data.getFr(), Field.Store.YES));
		// doc.add(new TextField("Footcite", fr94Data.getFootcite(), Field.Store.YES));
		// doc.add(new TextField("Cfrno", fr94Data.getCfrno(), Field.Store.YES));
		// doc.add(new TextField("Rindock", fr94Data.getRindock(), Field.Store.YES));
		// doc.add(new TextField("UsDept", fr94Data.getUsDept(), Field.Store.YES));
		// doc.add(new TextField("UsBureau", fr94Data.getUsBureau(), Field.Store.YES));
		// doc.add(new TextField("Imports", fr94Data.getImports(), Field.Store.YES));
		// doc.add(new TextField("Doctile", fr94Data.getDoctile(), Field.Store.YES));
		// doc.add(new TextField("Agency", fr94Data.getAgency(), Field.Store.YES));
		// doc.add(new TextField("Action", fr94Data.getAction(), Field.Store.YES));
		// doc.add(new TextField("Summary", fr94Data.getSummary(), Field.Store.YES));
		// doc.add(new TextField("Date", fr94Data.getDate(), Field.Store.YES));
		// doc.add(new TextField("Address,", fr94Data.getAddress(), Field.Store.YES));
		// doc.add(new TextField("Further", fr94Data.getFurther(), Field.Store.YES));
		// doc.add(new TextField("Supplem", fr94Data.getSupplem(), Field.Store.YES));
		// doc.add(new TextField("Signer", fr94Data.getSigner(), Field.Store.YES));
		// doc.add(new TextField("Signjob", fr94Data.getSignjob(), Field.Store.YES));
		// doc.add(new TextField("FrFiling", fr94Data.getFrFiling(), Field.Store.YES));
		// doc.add(new TextField("Billing", fr94Data.getBilling(), Field.Store.YES));
		// doc.add(new TextField("Footnote", fr94Data.getFootnote(), Field.Store.YES));
		// doc.add(new TextField("Footname", f
		HashMap<String, Float> boostMap = new HashMap<String, Float>();
        boostMap.put("Text", 5f); // test
        boostMap.put("Txt5", 2f);
        boostMap.put("Header", 7f);
        boostMap.put("Summary", 6f);
		boostMap.put("Action", 4f);
		boostMap.put("F", 3f);
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[] { "Text", "Txt5", "Header","Summary","Action","F"}, analyzer,boostMap);
		File outputFile = new File(OUTPUT_FILE);
		PrintWriter writer = new PrintWriter(outputFile, StandardCharsets.UTF_8);
		for (TopicModel topic : topics) {
			String queryS = QueryParser.escape(topic.getDescription().trim());
			Query query = queryParser.parse(queryS);
			ScoreDoc[] hits = isearcher.search(query, MAX_RESULTS).scoreDocs;
			int i = 0;
			for (ScoreDoc hit : hits) {
				Document hitDoc = isearcher.doc(hit.doc);
				// query-id 0 document-id rank score STANDARD
				writer.println(topic.getNumber() + " 0 " + hitDoc.get("Id") + " 0 " + hit.score + " STANDARD");
				i++;
			}
		}
		writer.close();
		ireader.close();
		indexDirectory.close();

		// indexWriter.close();
	}

	private static ArrayList<TopicModel> loadTopics(String topicPath) throws IOException {
		List<String> fileData = Files.readAllLines(Paths.get(topicPath), StandardCharsets.UTF_8);
		ArrayList<TopicModel> topics = new ArrayList<TopicModel>();
		String fieldType = "";
		TopicModel topic = new TopicModel();
		StringBuilder readString = new StringBuilder();
		for (String line : fileData) {
			if (line.length() > 0) {
				if (line.charAt(0) == '<') {
					String newFieldType = line.substring(1, line.indexOf('>'));
					switch (newFieldType) {
						case "top":
							topic = new TopicModel();
							break;
						case "/top":
							topics.add(topic);
							break;
						case "num":
							topic.setNumber(Integer.parseInt(line.substring(14).trim()));
							break;
						case "title":
							topic.setTitle(line.substring(8).trim());
					}
					if (!newFieldType.equals(fieldType)) {
						switch (fieldType) {
							case "desc":
								topic.setDescription(readString.toString().trim());
								break;
							case "narr":
								topic.setNarrative(readString.toString().trim());
								break;
						}
						readString = new StringBuilder();
					}
					fieldType = newFieldType;
				} else {
					readString.append(line.trim()).append(" ");
				}
			}
		}
		return topics;
	}
}