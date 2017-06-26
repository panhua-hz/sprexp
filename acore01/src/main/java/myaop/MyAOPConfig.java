package myaop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("myaop")
@EnableAspectJAutoProxy
public class MyAOPConfig {
	
}
