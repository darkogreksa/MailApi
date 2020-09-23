package uns.ac.rs.MailApi.lucene.indexing.analysers;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import uns.ac.rs.MailApi.lucene.filters.CyrilicToLatinFilter;

public class SerbianAnalyzer extends Analyzer {

    /**
     * An array containing some common English words
     * that are usually not useful for searching.
     */
    public static final String[] STOP_WORDS =
            {
                    "i","a","ili","ali","pa","te","da","u","po","na"
            };

    /**
     * Builds an analyzer.
     */
    public SerbianAnalyzer()
    {
    }


    @Override
    protected TokenStreamComponents createComponents(String arg0) {
        Tokenizer source = new StandardTokenizer();
        TokenStream result = new CyrilicToLatinFilter(source);
        result = new LowerCaseFilter(result);
        result = new StopFilter(result,StopFilter.makeStopSet(STOP_WORDS));
        return new TokenStreamComponents(source, result){
            @Override
            protected void setReader(final Reader reader) {
                super.setReader(reader);
            }
        };
    }

}
