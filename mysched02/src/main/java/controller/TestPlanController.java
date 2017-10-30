package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.web.bind.annotation.RestController;

import domain.TCase;
import domain.TPlan;
import domain.WebDriverEnv;
import repository.TCaseRepository;
import repository.WebDriverEnvRepository;
import service.SetupTestPlanService;

@RestController
public class TestPlanController {
	private static final Logger logger = LoggerFactory.getLogger(TestPlanController.class);
	@Autowired
	private TCaseRepository tcaseDal;
	@Autowired
	private WebDriverEnvRepository webDriverEnvDal;
	@Autowired
	private SetupTestPlanService testPlanServs;
	
	
	@RequestMapping("/testcases")
	public List<TCase> testcases(){
		return tcaseDal.findAll();
	}
	
	@RequestMapping("/testenvs")
	public List<WebDriverEnv> testEnvs(){
		return webDriverEnvDal.findAll();
	}
	
	@RequestMapping("/testplan")
	public Long viewTestPlan(){
		return 1l;
	}
	
	@RequestMapping(value="/testplan/runOnce", method=POST)
	public TPlan createTestPlan(
			@RequestParam(value="tcase_id") Long tcid, 
			@RequestParam(value="env_id") Long envid,
			@RequestParam(value="run_time")String dateStr)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date runDate = new Date();
		try{
			if (dateStr != null){
				runDate = df.parse(dateStr);
			}
		}catch(Exception e){
			logger.error("Error to parse date "+dateStr, e);
		}
		
		return testPlanServs.runOnce(tcid, envid, runDate);
	}

}
