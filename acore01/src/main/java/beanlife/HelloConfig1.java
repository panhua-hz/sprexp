package beanlife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackageClasses={WelcomeBean.class})
@PropertySource("classpath:beanlife/hello.properties")
public class HelloConfig1 {
	
	@Autowired
	Environment env;
	
	@Bean
	@Profile("test1")
	public HelloBean1 helloBean1(){
		return new HelloBean1Impl("tester1");
	}
	@Bean
	@Profile("test2")
	public HelloBean1 helloBean2(){
		return new HelloBean1Impl("tester2");
	}
	@Bean
	@Profile("test3")
	public HelloBean1 helloBean3(){
		return new HelloBean1Impl(env.getProperty("tester"));
	}
	@Bean
	@Profile("test4")
	public HelloBean1 helloBean4(){
		return new HelloBean1Impl(env.getProperty("tester"));
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
		return new PropertySourcesPlaceholderConfigurer();
	}
}
