package repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import domain.TCase;
import domain.TPlan;
import domain.TStep;
import domain.WebDriverEnv;
import exceptions.SchedRTException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ConfigCtxJPA.class)
public class TestDataInit {
	static final Logger logger = LoggerFactory.getLogger(TestDataInit.class);
	@Autowired
	TCaseRepository tcaseDal;
	@Autowired
	WebDriverEnvRepository webDriverEnvDal;
	@Autowired
	TPlanRepository tplanDal;
	
	@Test
	@Transactional
	public void saveTest1(){
		TCase tc1 = new TCase("testJD");
		tc1 = tcaseDal.save(tc1);
		
		List<TStep> steps = new ArrayList<>();
		TStep ts1 = new TStep(tc1.getId(), 1, "get","https://www.jd.com/",null);
		steps.add(ts1);
		TStep ts2 = new TStep(tc1.getId(), 2, "clickElement","link text=你好，请登录",null);
		steps.add(ts2);
		TStep ts3 = new TStep(tc1.getId(), 3, "setElementText","id=loginname","myusr");
		steps.add(ts3);
		TStep ts4 = new TStep(tc1.getId(), 4, "setElementText","id=nloginpwd","pwd");
		steps.add(ts4);
		
		tc1.setSteps(steps);
		tcaseDal.saveAndFlush(tc1);
	}
	
	@Test
	@Transactional
	public void saveTest2(){
		TCase tc1 = new TCase("testBD");
		tc1 = tcaseDal.save(tc1);
		
		List<TStep> steps = new ArrayList<>();
		TStep ts1 = new TStep(tc1.getId(), 1, "get","https://www.baidu.com/",null);
		steps.add(ts1);
		TStep ts2 = new TStep(tc1.getId(), 2, "setElementText","@id=\"kw\"","Cheese!");
		steps.add(ts2);
		TStep ts3 = new TStep(tc1.getId(), 3, "submit","@id=\"kw\"",null);
		steps.add(ts3);
		TStep ts4 = new TStep(tc1.getId(), 4, "asset","pageTitle startsWith","cheese!");
		steps.add(ts4);
		
		tc1.setSteps(steps);
		tcaseDal.saveAndFlush(tc1);
	}
	
	@Test
	@Transactional
	public void saveTest3(){
		TCase tc1 = new TCase("aloginTest");
		tc1 = tcaseDal.save(tc1);
		
		List<TStep> steps = new ArrayList<>();
		TStep ts1 = new TStep(tc1.getId(), 1, "get","https://www.jd.com/",null);
		steps.add(ts1);
		TStep ts2 = new TStep(tc1.getId(), 2, "clickElement","link text=你好，请登录",null);
		steps.add(ts2);
		TStep ts3 = new TStep(tc1.getId(), 3, "setElementText","id=loginname","myusr");
		steps.add(ts3);
		TStep ts4 = new TStep(tc1.getId(), 4, "setElementText","id=nloginpwd","pwd");
		steps.add(ts4);
		TStep ts5 = new TStep(tc1.getId(), 5, "assert","page status","logined");
		steps.add(ts5);
		
		tc1.setSteps(steps);
		tcaseDal.saveAndFlush(tc1);
	}
	@Test
	@Transactional
	public void saveENV(){
		List<WebDriverEnv> envList = new ArrayList<>();
		WebDriverEnv env1 = new WebDriverEnv("http://192.168.0.105:4444/wd/hub","RC");
		envList.add(env1);
		WebDriverEnv env2 = new WebDriverEnv("http://192.168.0.102:4444/wd/hub","Hub");
		envList.add(env2);
		
		webDriverEnvDal.save(envList);
		webDriverEnvDal.flush();
	}
	@Test
	public void getTest1(){
		TCase tc1 = tcaseDal.findOne(1L);
		logger.info("-----out for tcase1-----------------");
		logger.info(tc1.toString());
	}
	
	@Test
	@Transactional
	public void saveTestPlan(){
		TCase tcase = tcaseDal.findOne(1L);
		WebDriverEnv wdEnv = webDriverEnvDal.findOne(1L); 
		TPlan tplan = new TPlan(tcase, new Date(), wdEnv);
		tplan = tplanDal.save(tplan);
		logger.info("----------->");
		logger.info(tplan.toString());
		tplanDal.flush();
//		int i = 4;
//		if (i > 0){
//			throw new SchedRTException("This is an exception.");
//		}
	}
	
	@Test
	public void tetTestPlan(){
		TPlan tc1 = tplanDal.findOne(5L);
		logger.info("-----out for tcase1-----------------");
		logger.info(tc1.toString());
	}
}
