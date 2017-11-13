package tool;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.junit.Test;

public class ToolTest {
	@Test
	public void csvTest() throws IOException{
		Path path = Paths.get("F:/download/fileswap/", "datatype.csv");
		try(InputStream is = Files.newInputStream(path)){
			FileHander fh = new CSVFileHandler(is);
			RowPublisher rp = new PrintRowPublish();
			fh.doParseAndPublish(rp);
		}
	}
	
	@Test
	public void regexpTest(){
		Pattern ptc1 = Pattern.compile("\\w{9}");
		String str1 = "060505DB7";
		String str2 = "erf2d";
		
		
	}
	
}
