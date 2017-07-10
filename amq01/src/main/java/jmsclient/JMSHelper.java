package jmsclient;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class JMSHelper {
	
	public static void putMsg(String msg){
		//cf may have different provider.
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.0.106:61616"); 
		Connection conn = null;
		Session session = null;
		
		try {
			conn = cf.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = new ActiveMQQueue("sprexp.amq.q2");
			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			message.setText(msg);
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}finally{
			if (session != null){
				try{
					session.close();
				}catch(Exception e){
					
				}
			}
			if (conn != null){
				try{
					conn.close();
				}catch(Exception e){
					
				}
			}
		}
		
	}
	
	public static void getMsg(){
		//cf may have different provider.
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.0.106:61616"); 
		Connection conn = null;
		Session session = null;
		
		try {
			conn = cf.createConnection();
			conn.start();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = new ActiveMQQueue("sprexp.amq.q2");
			MessageConsumer consumer = session.createConsumer(destination);
			Message message = consumer.receive();
			TextMessage txtmessage = (TextMessage)message;
			System.out.println("Got Msg: "+txtmessage.getText());
			conn.stop();
		} catch (JMSException e) {
			e.printStackTrace();
		}finally{
			if (session != null){
				try{
					session.close();
				}catch(Exception e){
					
				}
			}
			if (conn != null){
				try{
					conn.close();
				}catch(Exception e){
					
				}
			}
		}
		
	}
	

	public static void main(String[] args) {
		putMsg("hello world3.");
		System.out.println("Put msg done!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getMsg();
		System.out.println("Get msg done!");
	}

}
