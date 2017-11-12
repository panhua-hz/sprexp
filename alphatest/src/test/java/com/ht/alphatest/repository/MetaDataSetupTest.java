package com.ht.alphatest.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ht.alphatest.domain.Platforms;
import com.ht.alphatest.domain.TargetEnv;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:context_app.xml"})
public class MetaDataSetupTest {
	static final Logger logger = LoggerFactory.getLogger(MetaDataSetupTest.class);
	@Autowired
	TargetEnvRepository targetEnvDal;
	
	@Test
	@Transactional
	public void setupTargetEnv(){
		TargetEnv env1 = new TargetEnv("MainTest", "test machine", Platforms.WEB.name(), "http://192.168.0.102:4444/wd/hub", null,null,"Win7",null);
		TargetEnv env2 = new TargetEnv("LC_peter", "A local machine", Platforms.WEB.name(), "http://192.168.0.105:4444/wd/hub", null,null,"Win7",null);
		targetEnvDal.save(env1);
		targetEnvDal.save(env2);		
	}
}
