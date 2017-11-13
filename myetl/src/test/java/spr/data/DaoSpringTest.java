package spr.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spr.data.DBConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DBConfig.class)
public class DaoSpringTest {
	@Autowired
	LockService lockService;
	
	@Test
    public void getLockTest() throws Exception{
		boolean getKey = lockService.acquireKey("load_file1", "getLockTest");
		System.out.println("GetKey: "+getKey);
	}
}
