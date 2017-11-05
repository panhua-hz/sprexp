package tasks;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.testng.TestNG;

import service.ExeTestPlanService;

public class ExeTestPlanJob extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(ExeTestPlanJob.class);
	@Autowired
	private ExeTestPlanService exeTestPlanService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Long tplanId = context.getJobDetail().getJobDataMap().getLong("tplanId");
		exeTestPlanService.execute(tplanId);
		
		//String[] argss = new String[]{"D:/myproject/stsgrd/sprexp/mysched/src/main/java/tasks/baidutest.xml"};
		String[] argss = new String[]{"http://localhost:8080/testng/baidutest.xml"};
		TestNG testng = TestNG.privateMain(argss, null);
		logger.info("TestNG status: "+testng.getStatus());
	}

}
