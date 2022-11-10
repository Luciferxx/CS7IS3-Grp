package tcd.CS7IS3.Utils;

import java.util.HashMap;
import org.apache.lucene.search.similarities.*;

public class SimilarityChoice {
	HashMap <String,Integer> choices = new HashMap<String,Integer>();

	public SimilarityChoice() {
		choices.put("BM25",1);
		choices.put("LMD",2);
		choices.put("BOOL",3);
		choices.put("CLASSIC",4);
	}
	 
	public Similarity Input_Choice(String choice) {
		 switch(choices.get(choice))
		 {
		 case 1:return new BM25Similarity();
		 case 2: return new LMDirichletSimilarity();  
		 case 3: return new BooleanSimilarity();
		 case 4:return new ClassicSimilarity();
		 }
		return null;
	}
}

