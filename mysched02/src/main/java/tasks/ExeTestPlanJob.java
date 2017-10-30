package tasks;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import service.ExeTestPlanService;

public class ExeTestPlanJob extends QuartzJobBean {
	
	@Autowired
	private ExeTestPlanService exeTestPlanService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Long tplanId = context.getJobDetail().getJobDataMap().getLong("tplanId");
		exeTestPlanService.execute(tplanId);
	}

}
