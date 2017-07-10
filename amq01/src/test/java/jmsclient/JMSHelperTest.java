package jmsclient;

import org.junit.Test;

public class JMSHelperTest {
	
	@Test
	public void putMsgTest(){
		JMSHelper.putMsg("Hi a test msg2!");
	}
	@Test
	public void getMsgTest(){
		JMSHelper.getMsg();
	}
}
