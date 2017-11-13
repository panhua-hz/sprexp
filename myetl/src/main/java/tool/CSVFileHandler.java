package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVFileHandler implements FileHander {
	
	//p217
	private static String regexp = "\\G(?:^|,)" //each cell main the start or start with previous comma
			+ "(?:"
			+ "\"("	//each cell may have quote surrounded
			+ "(?:[^\"]++" //in quote, no quote in content
			+ "|"	//or
			+ "\"\")*+" //have double quote
			+ ")\"" 
			+ "|"		//or
			+ "([^\",]*))"; //without quote and comma
	
	private static Pattern pMain = Pattern.compile(regexp);
	private static Pattern pQuote = Pattern.compile("\"\"");
	
	private InputStream is;
	public CSVFileHandler(InputStream is){
		this.is = is;
	}
	
	public static String[] parseCSVLine(String line){
		Matcher mMain = pMain.matcher("");
		Matcher mQuote = pQuote.matcher("");
		
		List<String> cellVals = new ArrayList<>();
		
		mMain.reset(line);
		while(mMain.find()){
			String field;
			if (mMain.start(2) >= 0)
				field = mMain.group(2);
			else
				field = mQuote.reset(mMain.group(1)).replaceAll("\"");
			
			cellVals.add(field);
		}
		String[] result = new String[cellVals.size()];
		return cellVals.toArray(result);
	}
	
	@Override
	public void doParseAndPublish(RowPublisher publisher) {
		try(BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
			//Stream<String> lines = rd.lines();
			String line = null;
			for(long i=1; (line=rd.readLine()) != null ;i++){
				String[] row = parseCSVLine(line);
				publisher.publish(i, line, row);
			}
			
		} catch (IOException e) {
			//TODO: exception log
			e.printStackTrace();
		}

	}

}
