package beanlife;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes=HelloConfig1.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test2")  //cloud be more profiles.
public class HelloTest1 {
	@Autowired
	HelloBean1 hellobean;
	
	@Test
	public void sayHelloTest(){
		hellobean.sayHello();
	}
}
