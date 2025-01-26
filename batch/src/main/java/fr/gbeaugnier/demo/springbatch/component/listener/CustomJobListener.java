package fr.gbeaugnier.demo.springbatch.component.listener;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(@NotNull JobExecution jobExecution) {
        log.info("Job execution started {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(@NotNull JobExecution jobExecution) {
        log.info("Job execution finished {} with status {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
    }
}
