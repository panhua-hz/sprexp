package controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;

import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequestMapping("/file")
public class FileUploadController {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	Environment env;
	
	@RequestMapping(method = POST)
    public String handleFileUpload(@RequestPart Part uploadFile){
		String webRootDir = env.getProperty("web.root.dir");
        String fileName = uploadFile.getSubmittedFileName();
        String fullName = webRootDir+"upload/" + fileName;
        
        if (fileName != null && !fileName.trim().isEmpty()){
            logger.info("Write to file {}",fileName);
            try {
                uploadFile.write(fullName);
            } catch (IOException e) {
                logger.error("handleFileUpload",e);
            }
        }
        
        return "redirect:/";
    }
}
