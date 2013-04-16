package tfidf;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.Test;


public class BetterStringTokenizerTest extends TestCase {

	@Test
	public void testSentenceWithPunctuation() {
		
		String sentence = "Marcello, store... Finally!!!    Are you coming - algo (as)/ ?";
		
		Pattern p = Pattern.compile("\\w+");
		Matcher m = p.matcher(sentence);
		String finalResult = "";
		while(m.find()) {
			finalResult += m.group() + ",";
		}
		String finalString = "Marcello,store,Finally,Are,you,coming,algo,as,";
		assertEquals(finalString, finalResult);
	}
}
