package pt.arquivo.spellchecker.rules.portuguese;

import pt.arquivo.spellchecker.rules.NormalizingRule;

import org.apache.log4j.Logger;

/**
 * Diacritics substitution rule
 * @author Miguel Costa
 */
public class DiacriticsRule implements NormalizingRule {

	private static Logger logger = null;

	public DiacriticsRule() {
		logger = Logger.getLogger(DiacriticsRule.class.getName());
	}

	public String normalizeByRule(String word) {
		String normalized = null;

		char cWordWithAccents[]=word.toCharArray();
		for (int i=0;i<cWordWithAccents.length;i++) {

			switch(cWordWithAccents[i]) {
				case 0x87:					
				case 0x88:					
				case 0x89:					
				case 0x8B: cWordWithAccents[i]='a'; break;	

				case 0x8E: 					
				case 0x8F:					
				case 0x90: cWordWithAccents[i]='e'; break;	

				case 0x92: cWordWithAccents[i]='i'; break;	
	
				case 0x97:					
				case 0x99:					
				case 0x9B: cWordWithAccents[i]='o'; break;	
	
				case 0x9C: cWordWithAccents[i]='u'; break;	
	
				case 0x8D: cWordWithAccents[i]='c'; break;	
			}
		}
		normalized = new String(cWordWithAccents);

		logger.debug("Word being evaluated:\t"+ word);
		logger.debug("Word normalized:\t"+ normalized);		

		return normalized;
	}
}
