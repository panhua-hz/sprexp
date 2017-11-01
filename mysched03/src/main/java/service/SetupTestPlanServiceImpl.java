package service;

import java.text.ParseException;
import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.TCase;
import domain.TPlan;
import domain.WebDriverEnv;
import exceptions.SchedRTException;
import integrate.SpringQuartzUtils;
import repository.TCaseRepository;
import repository.TPlanRepository;
import repository.WebDriverEnvRepository;
import tasks.ExeTestPlanJob;

@Component
public class SetupTestPlanServiceImpl implements SetupTestPlanService {
	static final Logger logger = LoggerFactory.getLogger(SetupTestPlanServiceImpl.class);
	@Autowired
	TCaseRepository tcaseDal;
	@Autowired
	WebDriverEnvRepository webDriverEnvDal;
	@Autowired
	TPlanRepository tplanDal;
	@Autowired
	SchedulerFactoryBean schedFactory;

	@Override
	@Transactional(rollbackFor=RuntimeException.class)
	public TPlan runOnce(Long testcaseId, Long env, Date runTime) {
		String jobName = new StringBuffer(testcaseId.toString()).append(env).append(runTime.getTime()).toString();
		
		TCase tcase = tcaseDal.findOne(testcaseId);
		WebDriverEnv wdEnv = webDriverEnvDal.findOne(env);
		TPlan tplan = new TPlan(tcase, runTime, wdEnv);
		tplan = tplanDal.save(tplan);

		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("tplanId", tplan.getId());

		JobDetailFactoryBean jdfb = SpringQuartzUtils.createJobDetail(ExeTestPlanJob.class, jobDataMap);
		jdfb.setBeanName("RunOnce_"+jobName);
		jdfb.afterPropertiesSet();

		SimpleTriggerFactoryBean stfb = SpringQuartzUtils.createTriggerForRunOnceAt(jdfb.getObject(), runTime);
		stfb.setBeanName("STG_"+jobName);
		stfb.afterPropertiesSet();

		try {
			schedFactory.getScheduler().scheduleJob(jdfb.getObject(), stfb.getObject());
		} catch (SchedulerException e) {
			logger.error("springquartz scheduleJob error.", e);
			throw new SchedRTException(e);
		}
		tplanDal.flush();
		
		return tplan;
	}

	@Override
	@Transactional
	public TPlan repeatRun(Long testcaseId, Long env, String cronExp) {
		TCase tcase = tcaseDal.findOne(testcaseId);
		WebDriverEnv wdEnv = webDriverEnvDal.findOne(env);
		TPlan tplan = new TPlan(tcase, cronExp, wdEnv);
		tplan = tplanDal.save(tplan);

		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("tplanId", tplan.getId());

		JobDetailFactoryBean jdfb = SpringQuartzUtils.createJobDetail(ExeTestPlanJob.class, jobDataMap);
		jdfb.setBeanName("exeTestPlanJob");
		jdfb.afterPropertiesSet();

		CronTriggerFactoryBean stfb = SpringQuartzUtils.createCronTrigger(jdfb.getObject(), cronExp);
		stfb.setBeanName("dynamicJobBeanTrigger");
		try {
			stfb.afterPropertiesSet();
		} catch (ParseException e1) {
			logger.error("set cron trigger fail for cronexp: "+cronExp, e1);
			throw new SchedRTException(e1);
		}

		try {
			schedFactory.getScheduler().scheduleJob(jdfb.getObject(), stfb.getObject());
		} catch (SchedulerException e) {
			logger.error("springquartz scheduleJob error.", e);
			throw new SchedRTException(e);
		}
		tplanDal.flush();
		return tplan;
	}
}
