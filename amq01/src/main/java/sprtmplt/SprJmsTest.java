package sprtmplt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes=ConfigJMS.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SprJmsTest {
	@Autowired
	PutMsgService putMsgService;
	
	@Test
	public void putMsgTest(){
		putMsgService.putMsg("Spring hello jms.");
	}
	
	@Test
	public void getMsgTest(){
		String message = putMsgService.getMsg();
		System.out.println(message);
	}
	
	@Test
	public void getCvtMsgTest(){
		String message = putMsgService.getCvtMsg();
		System.out.println(message);
	}
}
