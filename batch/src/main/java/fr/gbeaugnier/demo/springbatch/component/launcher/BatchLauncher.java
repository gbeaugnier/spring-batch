package fr.gbeaugnier.demo.springbatch.component.launcher;

import fr.gbeaugnier.demo.springbatch.client.kafka.model.MinioEvent;
import fr.gbeaugnier.demo.springbatch.client.kafka.model.TopicName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static fr.gbeaugnier.demo.springbatch.config.ParameterName.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchLauncher {

    private final JobLauncher jobLauncher;
    private final Job job;

    @KafkaListener(topics = TopicName.DATA_EVENT, groupId = TopicName.GROUP_EVENT)
    public void run(MinioEvent message) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        JobParameters parameters = new JobParametersBuilder()
                .addString(BUCKET_NAME.name(), message.getBucketName())
                .addString(RECORD_KEY.name(), message.getRecordKey())
                .addString(OBJECT_VERSION.name(), message.getObjectVersion())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job, parameters);
        log.info("Job execution status: {}", jobExecution.getStatus());

    }

}
