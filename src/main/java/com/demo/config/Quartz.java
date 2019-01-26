package com.demo.config;

import com.demo.jobs.DummyJob;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class Quartz {

    @Bean
    public JobDetailFactoryBean removeExpiredPendingWalletTransfersQuartzJobBean() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(DummyJob.class);
        jobDetailFactory.setDescription("DummyJob");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean dummyCronTrigger(JobDetail dummyJobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(dummyJobDetail);
        cronTriggerFactoryBean.setGroup("DEFAULT_GROUP");
        cronTriggerFactoryBean.setCronExpression("0 0/1 * * * ?");
        return cronTriggerFactoryBean;
    }

}
