package client;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientTest {
    private static Logger logger = LoggerFactory.getLogger(HttpClientTest.class);
    @Test
    public void testPostHelloXml(){
        logger.info("testPostHelloXml");
        String urlservlet = "http://panhua-PC:8080/esprmvc0/resp";
        HttpClientMain.httpCallerTest(urlservlet);
    }
    
}
