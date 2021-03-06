package pt.arquivo.spellchecker;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import java.net.*;
import org.json.*;


/**
 * SpellChecker based on Bing search API
 * https://www.microsoft.com/en-us/bing/apis/bing-spell-check-api
 * @author Pedro Gomes
 */
public class SpellChecker {
    private static Logger logger = Logger.getLogger(SpellChecker.class.getName());

    static String host = "https://api.bing.microsoft.com";
    static String path = "/v7.0/SpellCheck";
    static String mode = "spell";

    /**
     * Suggests words using Bing.
     *
     * @param queryTerm      Query term.
     * @param lang           Dictionary language, "pt-PT" or "en-US".
     * @param key            Private Key API from Bing.
     * @return
     */
    public static String suggestSimilarBing(String queryTerm, String lang, String key) throws IOException {
    	
    	//I don't think we need that many parameters.
    	//String[] langParts = lang.split("-");
    	//String params = "?mkt=" + lang + "&mode=" + mode + "&cc=" + langParts[1] + "&setLang=" + lang.toLowerCase();

        logger.info("queryTerm: "+ queryTerm);
        String encoded_text = URLEncoder.encode (queryTerm, "UTF-8");
        logger.info("params: "+ host + path + "?mkt=" + lang + "&" + "mode=" + mode + "&" + "text=" + encoded_text);
        
        URL url = new URL(host + path + "?mkt=" + lang + "&" + "mode=" + mode + "&" + "text=" + encoded_text);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
 	 	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
 		connection.setRequestProperty("Ocp-Apim-Subscription-Key", key);
 		connection.setDoOutput(true);
		
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String jsonOutput = "";
        String line;
        while ((line = in.readLine()) != null) {
        	jsonOutput = jsonOutput + line;
        }
        in.close();
        logger.info("word: "+ jsonOutput);

        JSONObject jsonObject = new JSONObject(jsonOutput);
        JSONArray flaggedTokens = jsonObject.getJSONArray("flaggedTokens");
        String replaceString = queryTerm;
        for (int i = 0; i < flaggedTokens.length(); i++) {
        	JSONArray suggestions = flaggedTokens.getJSONObject(i).getJSONArray("suggestions");
	        String token = flaggedTokens.getJSONObject(i).getString("token");
	        String suggestion = suggestions.getJSONObject(0).getString("suggestion");
	        replaceString = replaceString.replace(token, suggestion);
        }

        return replaceString;
    }
}
