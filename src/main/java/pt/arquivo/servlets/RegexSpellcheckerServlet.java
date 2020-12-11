package pt.arquivo.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.StringBuffer;
import java.nio.charset.Charset;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import pt.arquivo.spellchecker.SpellChecker;

/**
 * Servlet implementation for Spellchecker
 * @author Pedro Gomes
 */
public class RegexSpellcheckerServlet extends HttpServlet {

	private static final String QUERY_TERM_REGEX = "-?([^\"\\s-]+)";
	private static String key="";
	private static Logger logger=null; 

	private Pattern pattern;

	private String encoding = "UTF-8";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegexSpellcheckerServlet() {
        super();
    }

    /**
     * 
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);        
        logger = Logger.getLogger(RegexSpellcheckerServlet.class.getName());
        key = config.getInitParameter("key");
		pattern = Pattern.compile(QUERY_TERM_REGEX);

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		if (null == request.getCharacterEncoding()) {
			// Respect the client-specified character encoding
			// (see HTTP specification section 3.4.1)
			logger.info("changing to default encoding");
			request.setCharacterEncoding( encoding );
		}
		
		String correction = "";
		String query = request.getParameter("query");
		String lang = request.getParameter("l");

		if (lang == null) {
			lang = "pt-PT";
		} else if ( lang.equals("en") ) {
			lang = "en-US";
		} else {
			lang = "pt-PT";
		}

		if (query != null && lang != null) {

			logger.info("checking query: "+ query);

			String suggestion = "";
			
			try {
				suggestion = SpellChecker.suggestSimilarBing(query, lang, key);
				if ( !query.equals( suggestion ) ) {
					logger.info("suggestion: "+ suggestion);
					correction = "<em>"+ suggestion +"</em>";
				}				
			} catch (IOException e) {			
				throw new IOException(e);
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out=response.getWriter();
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
			out.println("<html>");
				out.println("<head>");
					out.println("<title>Query Spellchecker</title>");
				out.println("</head>");
				out.println("<body>");
					out.println("<h3>Query Spellchecker</h3>");
					out.println("<h5>Query:</h5>");
						out.println("<div id=\"query\">");	    
						out.println( query );
					out.println("</div>");
					out.println("<h5>Correction:</h5>");
					out.println("<div id=\"correction\">");	    
						out.println( correction );
					out.println("</div>");
				out.println("</body>");
			out.println("</html>");	  
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * Check if the term is a search operator
	 */
	private boolean isOperator(String term) {
		boolean result = false;		

		if ( term.startsWith("type:") ) {
			result = true;
		} else if ( term.startsWith("site:") ) {
			result = true;
		} else if ( term.startsWith("sort:")) {
			result = true;
		}

		return result;
	}

}