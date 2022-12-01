package tcd.CS7IS3.Utils;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.CapitalizationFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import tcd.CS7IS3.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

// https://www.baeldung.com/lucene-analyzers
public class customAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String s) {
        StandardTokenizer src = new StandardTokenizer();
        TokenStream result = new LowerCaseFilter(src);
        result = new LowerCaseFilter(result);
        result = new PorterStemFilter(result);
        result = new KStemFilter(result);
        result = new EnglishPossessiveFilter(result);
        result = new CapitalizationFilter(result);
        result = new StopFilter(result, getStopWords());
        return new TokenStreamComponents(src, result);
    }

    private CharArraySet getStopWords() {
        String str = "";
        try {
            str = new String(Files.readAllBytes(Paths.get(Main.STOPWORD_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> stopwordList = Arrays.asList(str.toLowerCase().split("\n"));
        return new CharArraySet(stopwordList, true);
    }
}
