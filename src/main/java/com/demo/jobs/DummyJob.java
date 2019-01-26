package com.demo.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.Instant;

public class DummyJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(String.format("Running %s at %s", DummyJob.class.getName(), Instant.now()));
    }
}
