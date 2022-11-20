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
		Analyzer analyzer = analyzer_choice.Input_Choice(LuceneContstants.chosen_Analyzer);
		Similarity similarity = similarity_choice.Input_Choice(LuceneContstants.chosen_Similarity);
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
		isearcher.setSimilarity(similarity);
		// TODO: @Luciferxx update fields
		/*
		 * Boost queries using the common words in the description and the narrative.
		 * Also boost queries using the title
		 */
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