package client;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static client.IOUtil.*;

public class HttpClientMain {
    private static Logger logger = LoggerFactory.getLogger(HttpClientMain.class);
    
    public static void main(String[] args) {
    }
    public static void httpCallerTest(String urlStr){
        //注意url不能使用localhost.否则不能被fiddler所监控
        //localhost，自回路，不会通过proxy.
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpCnn = (HttpURLConnection) url.openConnection();
            
            //Set proxy for fiddler
            System.setProperty("http.proxyHost", "localhost"); 
            System.setProperty("http.proxyPort", "8888"); 
            System.setProperty("https.proxyHost", "localhost");
            System.setProperty("https.proxyPort", "8888");
            System.setProperty("http.proxySet", "true");
            System.setProperty("https.proxySet", "true");
            
            httpCnn.setRequestMethod("POST");
            httpCnn.setRequestProperty("Content-type", "application/xml; charset=UTF-8");
            httpCnn.setRequestProperty("Accept", "application/xml");
            
            httpCnn.setDoOutput(true);
            
            OutputStream wos = httpCnn.getOutputStream();
            InputStream fis = HttpClientMain.class.getResourceAsStream("book.xml");
            iowriter(fis,wos);
                 
            httpCnn.connect();
            
            InputStream wis = httpCnn.getInputStream();
            Reader wrd = new InputStreamReader(wis,"UTF-8");
            StringWriter sw = new StringWriter();
            rwriter(wrd, sw);
            
            logger.info(sw.toString());
            httpCnn.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
