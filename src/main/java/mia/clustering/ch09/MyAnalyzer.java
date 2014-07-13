/*
 * Source code for Listing 9.5
 * 
 */

package mia.clustering.ch09;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.Tokenizer;

public class MyAnalyzer extends Analyzer {

// TODO: will it work, or it's better to return to combination of this & previous approaches?
	
  @Override
  protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
	  Tokenizer source = new StandardTokenizer(Version.LUCENE_CURRENT, reader);
	  TokenStream filter = new StandardFilter(Version.LUCENE_CURRENT, source);
	  filter = new LowerCaseFilter(Version.LUCENE_CURRENT, filter);
	  filter = new StopFilter(Version.LUCENE_CURRENT, filter, StandardAnalyzer.STOP_WORDS_SET);

	  return new TokenStreamComponents(source, filter);
  }
}
