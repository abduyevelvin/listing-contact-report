package com.listing.contact.report.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class JobLauncherConfig {
    private static final Logger LOG = LoggerFactory.getLogger(JobLauncherConfig.class);

    private final JobLauncher jobLauncher;
    private final Job listingJob;
    private final Job contactJob;

    @Value("${job.enabled-for}")
    public String jobEnabledFor;

    public JobLauncherConfig(JobLauncher jobLauncher, @Qualifier("listingsJob") Job listingJob
            , @Qualifier("contactsJob") Job contactJob) {
        this.jobLauncher = jobLauncher;
        this.listingJob = listingJob;
        this.contactJob = contactJob;
    }

    public void launchJobs() {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);

        try {
            LOG.info("Launch job listings and contacts Data Import Job ....");

            if("All".equalsIgnoreCase(jobEnabledFor) || "LISTINGS".equalsIgnoreCase(jobEnabledFor)) {
                JobExecution listingJobExecution = jobLauncher.run(listingJob, parameters);
                LOG.info("listingJobExecution completed, status : {} ", listingJobExecution.getExitStatus());
            }
            if("All".equalsIgnoreCase(jobEnabledFor) || "CONTACTS".equalsIgnoreCase(jobEnabledFor)) {
                JobExecution contactJobExecution = jobLauncher.run(contactJob, parameters);
                LOG.info("contactJobExecution completed, status : {} ", contactJobExecution.getExitStatus());
            }

            LOG.info("All job executions completed, status : {} ", "All Jobs executed !!!...");

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException exception) {
            LOG.error("Exception message : {} " ,  exception.getMessage());
        }
    }
}
