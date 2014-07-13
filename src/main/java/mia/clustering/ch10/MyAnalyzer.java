/*
 * Source Code for Listing 10.4
 * 
 */
package mia.clustering.ch10;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.Tokenizer;

public class MyAnalyzer extends Analyzer {
  
	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		Tokenizer source = new StandardTokenizer(Version.LUCENE_CURRENT, reader);
		TokenStream filter = new LowerCaseFilter(Version.LUCENE_CURRENT, source);
		filter = new LengthFilter(true, filter, 3, 50);
		filter = new StopFilter(Version.LUCENE_CURRENT, filter, StandardAnalyzer.STOP_WORDS_SET);
		filter = new PorterStemFilter(filter);

		return new TokenStreamComponents(source, filter);
	}
}
