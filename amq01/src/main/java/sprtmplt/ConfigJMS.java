package sprtmplt;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.SimpleMessageConverter;

@Configuration
@EnableJms
@ComponentScan("sprtmplt")
public class ConfigJMS {
	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory("tcp://192.168.0.106:61616");
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate(connectionFactory());
		template.setMessageConverter(new SimpleMessageConverter());
		return template;
	}

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		//factory.setDestinationResolver(destinationResolver());
		factory.setConcurrency("3-10");
		return factory;
	}
	// @Bean
	// public Destination queue1(){
	// return new ActiveMQQueue("sprexp.amq.q1");
	// }
	//
	// @Bean
	// public Destination topic1(){
	// return new ActiveMQTopic("sprexp.amq.tpc1");
	// }

}
