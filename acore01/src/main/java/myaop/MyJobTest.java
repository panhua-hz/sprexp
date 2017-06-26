package myaop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes=MyAOPConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MyJobTest {
	@Autowired
	MyJob myjob;
	
	@Test
	public void exeJob1(){
		myjob.exeJob();
		myjob.exeJob("Andrew");
		myjob.exeJob("Jerry");
	}
	
	@Test
	public void exeJobTest3(){
		myjob.exeJob2("Jerry");
		String result = myjob.exeAnnJob3("Andrew", "Pan");
		System.out.println("execute result "+result);
	}
	
	@Test
	public void addMethodTest(){
		Encoreable enc = (Encoreable)myjob;
		enc.performEncore();
	}
}
