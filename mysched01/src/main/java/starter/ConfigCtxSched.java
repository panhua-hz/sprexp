package starter;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import tasks.SampleJob;

@Configuration
// @ComponentScan("tasks")
public class ConfigCtxSched {
	//@Bean
	public JobDetail sampleJobDetail() {
		return JobBuilder.newJob(SampleJob.class).withIdentity("sampleJob").usingJobData("name", "World").storeDurably()
				.build();
	}

	//@Bean
	public Trigger sampleJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
				.repeatForever();

		return TriggerBuilder.newTrigger().forJob(sampleJobDetail()).withIdentity("sampleTrigger")
				.withSchedule(scheduleBuilder).build();
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(){
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobDetails(sampleJobDetail());
		factory.setTriggers(sampleJobTrigger());
//		try {
//			factory.getScheduler().scheduleJob(sampleJobDetail(), sampleJobTrigger());
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
		return factory;
	}
}
