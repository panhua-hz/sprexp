package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import domain.*;
import repository.*;

@Component("exeTestPlanService")
public class ExeTestPlanServiceImpl implements ExeTestPlanService {
	static final Logger logger = LoggerFactory.getLogger(ExeTestPlanServiceImpl.class);
	
//	@Autowired
//	private TCaseRepository tcaseDal;
//	@Autowired
//	private WebDriverEnvRepository webDriverEnvDal;
	@Autowired
	private TPlanRepository tplanDal;
	
	
	@Override
	public void execute(Long testPlan) {
		TPlan tplan = tplanDal.findOne(testPlan);
		logger.info("---->call test plan ---->"+testPlan);
		logger.info(tplan.toString());
		logger.info("call test plan done.");
	}


	@Override
	public void execute() {
		execute(1L);
	}

}
