package controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestCaseXmlController {
	private static final Logger logger = LoggerFactory.getLogger(TestCaseXmlController.class);
	
	@RequestMapping(value = "/testng/{xmlname}", method = GET)
	public void helloXml(@PathVariable String xmlname, HttpServletResponse response){
		logger.info("Get xml: "+xmlname);
		Path p = Paths.get("D:/myproject/stsgrd/sprexp/mysched/src/main/java/tasks/baidutest.xml");
		try {
			byte[] contents = Files.readAllBytes(p);
			String contentStr = new String(contents, StandardCharsets.UTF_8);
			writeResponseXml(response, contentStr);
		} catch (IOException e) {
			logger.error(xmlname,e);
		}
	}
	
	public static void writeResponseXml(HttpServletResponse response, String outputXml) throws IOException {
		response.setContentType("application/xml; charset=UTF-8;pageEncoding=UTF-8");
		response.getWriter().append(outputXml);
	}
}
