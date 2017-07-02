package config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import(value = { ConfigCtxWebJstl.class, ConfigCtxWebStdMultPart.class })
@EnableWebMvc
@ComponentScan("controller")
public class ConfigCtxWeb extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// This will change the static resouce dir mapping.
	// @Override
	// public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	// super.addResourceHandlers(registry);
	// registry.addResourceHandler("/html/**").addResourceLocations("/WEB-INF/html/");
	// }

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/i18n/messages");
		messageSource.setCacheSeconds(10);
		return messageSource;
	}

	@Override
	public Validator getValidator() {
		//for form validation message i18n
		// return super.getValidator();
		LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
		factory.setValidationMessageSource(messageSource());
		return factory;
	}
}
