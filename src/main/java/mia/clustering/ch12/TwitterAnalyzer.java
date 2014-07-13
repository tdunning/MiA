/*
 * Source code for Listing 12.3
 * 
 */
package mia.clustering.ch12;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.Tokenizer;

public class TwitterAnalyzer extends Analyzer {
  private DoubleMetaphone filter = new DoubleMetaphone();

	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		Tokenizer source = new StandardTokenizer(Version.LUCENE_CURRENT, reader);
		TokenStream result = new StopFilter(Version.LUCENE_CURRENT, source, StandardAnalyzer.STOP_WORDS_SET);
		result = new PorterStemFilter(result);

		//....
			
		return new TokenStreamComponents(source, result);
	}

	
  // @Override
  // public TokenStream tokenStream(String fieldName, Reader reader) {
  //   final TokenStream result = new PorterStemFilter(new StopFilter(
  //       true, new StandardTokenizer(Version.LUCENE_CURRENT, reader),
  //       StandardAnalyzer.STOP_WORDS_SET));
    
  //   TermAttribute termAtt = (TermAttribute) result
  //       .addAttribute(TermAttribute.class);
  //   StringBuilder buf = new StringBuilder();
  //   try {
  //     while (result.incrementToken()) {
  //       String word = new String(termAtt.termBuffer(), 0, termAtt
  //           .termLength());
  //       buf.append(filter.encode(word)).append(" ");
        
  //     }
  //   } catch (IOException e) {
  //     e.printStackTrace();
  //   }
  //   return new WhitespaceTokenizer(Version.LUCENE_CURRENT, new StringReader(buf.toString()));
  // }
}
