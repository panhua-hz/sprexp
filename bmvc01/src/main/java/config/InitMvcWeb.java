package config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/**
 * Initiate part of web.xml related configure here.
 * But mainly for DispatcherServlet.
 * @author panhua
 *
 */
public class InitMvcWeb extends AbstractAnnotationConfigDispatcherServletInitializer {
	public static final String CHAR_ENCODING = "UTF-8";
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {ConfigCtxRoot.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {ConfigCtxWeb.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
    protected void customizeRegistration(Dynamic registration) {
        //MultipartConfigElement multipartConfig = new MultipartConfigElement("D:/myproject/stsgrd/sprexp/bmvc01/tmp");
        MultipartConfigElement multipartConfig = new MultipartConfigElement(""); //TOCHECK: set to "" can work. Not sure for big file.
        registration.setMultipartConfig(multipartConfig);
    }
	
	@Override
    protected Filter[] getServletFilters() {
		//setup filters here.
        final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding(CHAR_ENCODING);
        encodingFilter.setForceEncoding(true);
        return new Filter[] { encodingFilter };
    }
}
