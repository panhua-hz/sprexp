package beanlife;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloMain1 {

	public static void main(String[] args) {
		//System.setProperty("spring.profiles.active", "test1,dev");
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(HelloConfig1.class);
		HelloBean1 hello1 = context.getBean(HelloBean1.class);
		hello1.sayHello();
		
		WelcomeBean welcomeBean= context.getBean(WelcomeBean.class);
		welcomeBean.sayHello();
		
		context.close();
	}

}
