package pt.arquivo.spellchecker.tests;

import pt.arquivo.spellchecker.SpellChecker;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.junit.Test;


/**
 * Test case for spellchecker
 * @author Pedro Gomes
 */
public class TestSpellChecker {

	private static String key="";
	
	public void init(ServletConfig config) throws ServletException {     
        key = config.getInitParameter("key");
    }
    /**
     * Test spellchecker algorithms 
     * @throws IOException
     */
    @Test
	public void testSpellChecker() throws IOException {
		System.out.println("Starting spellchecker...");
        String suggestion = SpellChecker.suggestSimilarBing("lisbo", "pt-PT", key);
        assertEquals("Correction ", suggestion, "lisboa");
	}
}	