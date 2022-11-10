package tcd.CS7IS3.Utils;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
public class AnalyzerChoice {
	HashMap <String,Integer> choices = new HashMap<String,Integer>();

	public AnalyzerChoice() {
		choices.put("STANDARD",1);
		choices.put("WHITESPACE",2);
		choices.put("ENGLISH",3);
		choices.put("SIMPLE",4);
		choices.put("CUSTOM",5);


	}
	public Analyzer Input_Choice(String choice) {
		 switch(choices.get(choice))
		 {
		 case 1: return new StandardAnalyzer(); 
		 case 2:return new WhitespaceAnalyzer(); 
		//  case 3:return new EnglishAnalyzer(Indexer.getStopWords());
		 case 4:return new SimpleAnalyzer();
		//  case 5:return new CustomAnalyzer();
		 }
		return null;
	}
	
}
