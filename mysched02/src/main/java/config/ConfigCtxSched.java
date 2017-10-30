package config;

import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import integrate.AutowireCapableJobFactory;
import integrate.SpringQuartzUtils;
import tasks.ExeTestPlanJob;

@Configuration
@ComponentScan("service")
public class ConfigCtxSched {

	@Bean
	public JobFactory jobFactory() {
		AutowireCapableJobFactory jobFactory = new AutowireCapableJobFactory();
		return jobFactory;
	}
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory){
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setAutoStartup(true);
		//factory.setDataSource(dataSource);
		factory.setJobFactory(jobFactory);
		//factory.setQuartzProperties(quartzProperties());
		
//		JobDetailFactoryBean jdfb = SpringQuartzUtils.createJobDetail(ExeTestPlanJob.class);
//		jdfb.setBeanName("dynamicJobBean");
//		jdfb.afterPropertiesSet();
//		
//		SimpleTriggerFactoryBean stfb = SpringQuartzUtils.createTrigger(jdfb.getObject(),5000L);
//		stfb.setBeanName("dynamicJobBeanTrigger");
//		stfb.afterPropertiesSet();
//
//		factory.setJobDetails(jdfb.getObject());
//		factory.setTriggers(stfb.getObject());
		
//		try {
//			factory.getScheduler().scheduleJob(sampleJobDetail(), sampleJobTrigger());
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		return factory;
	}
}
