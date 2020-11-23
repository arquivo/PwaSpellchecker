package pt.arquivo.spellchecker.rules.portuguese;

import pt.arquivo.spellchecker.rules.NormalizingRule;

import org.apache.log4j.Logger;

/**
 * Substitute probable mistakes of multiple chars difference
 * @author Miguel Costa
 */
public class MultipleSubstituteRule implements NormalizingRule {
	private static Logger logger = null;

	public MultipleSubstituteRule() {
		logger = Logger.getLogger(MultipleSubstituteRule.class.getName());
	}

	public String normalizeByRule(String word) {
		String normalized = null;	
		StringBuffer sbuf=new StringBuffer();
		char lastChars[]=new char[3];
		int nChars=0;

		logger.debug("Word being evaluated:\t"+ word);

		for (int i=0;i<word.length();i++) {			
			if (nChars<3) {
				lastChars[nChars]=word.charAt(i);
				nChars++;
			}

			logger.debug("Last chars:\t"+ lastChars[0] + lastChars[1] + lastChars[2]);
			
			if (nChars==3) {
				// three chars
				if (compareThreeChars(lastChars,"sse")) {
					sbuf.append("ce");
					nChars=0;
				}
				else if (compareThreeChars(lastChars,"ssi")) {
					sbuf.append("ci");
					nChars=0;
				}
				else {				
					sbuf.append(lastChars[0]);					
					nChars=2;										
				}
				
				if (nChars==2) {
					lastChars[0]=lastChars[1];
					lastChars[1]=lastChars[2];
				}
				else if (nChars==1) {
					lastChars[0]=lastChars[2];
				}
			}						
		}
		for (int i=0;i<nChars;i++) {
			sbuf.append(lastChars[i]);
		}
		normalized = sbuf.toString();

		logger.debug("Word normalized:\t"+ normalized);

		return normalized;
	}
	
	private boolean compareTwoChars(char[] carray, String s) {
		return (carray[0]==s.charAt(0) && carray[1]==s.charAt(1));
	}
	
	private boolean compareThreeChars(char[] carray, String s) {
		return (carray[0]==s.charAt(0) && carray[1]==s.charAt(1) && carray[2]==s.charAt(2));
	}
}
