package sprtmplt;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PutMsgServiceImpl implements PutMsgService {

	@Autowired
	JmsTemplate jmsTemplate;
	
	@Override
	public void putMsg(String message) {
		jmsTemplate.send("sprexp.amq.q1", session->{return session.createTextMessage(message);});
	}

	@Override
	public String getMsg() {
		Message msg = jmsTemplate.receive("sprexp.amq.q1");
		TextMessage message = (TextMessage)msg;
		try {
			return message.getText();
		} catch (JMSException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	@Override
	public String getCvtMsg() {
		return (String)jmsTemplate.receiveAndConvert("sprexp.amq.q1");
	}

	@Override
	@JmsListener(destination = "sprexp.amq.q1")
	public void autoGetMsg(String message) {
		System.out.println("Listening msg: "+message);
	}

}
