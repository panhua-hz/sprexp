package config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import integrate.AutowireCapableJobFactory;

@Configuration
@ComponentScan("service")
public class ConfigCtxSched {

	@Bean
	public JobFactory jobFactory() {
		AutowireCapableJobFactory jobFactory = new AutowireCapableJobFactory();
		return jobFactory;
	}
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) throws IOException{
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setAutoStartup(true);
		factory.setDataSource(dataSource);
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());
		
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
	
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
}
