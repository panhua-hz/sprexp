package config;

import javax.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

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
    protected Filter[] getServletFilters() {
        final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding(CHAR_ENCODING);
        encodingFilter.setForceEncoding(true);
        return new Filter[] { encodingFilter };
    }
}
