package fr.gbeaugnier.demo.springbatch.component.step.tasklet;

import fr.gbeaugnier.demo.springbatch.client.bucket.BucketClient;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static fr.gbeaugnier.demo.springbatch.config.ParameterName.*;

@Component
@RequiredArgsConstructor
public class DownloadFileTasklet implements Tasklet, StepExecutionListener {

    private final BucketClient bucketClient;
    private String filename;

    @Override
    public RepeatStatus execute(StepContribution contribution, @NotNull ChunkContext chunkContext) {

        JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
        String bucketName = Objects.requireNonNull(jobParameters.getParameter(BUCKET_NAME.name())).getValue().toString();
        String recordKey = Objects.requireNonNull(jobParameters.getParameter(RECORD_KEY.name())).getValue().toString();
        String objectVersion = Objects.requireNonNull(jobParameters.getParameter(OBJECT_VERSION.name())).getValue().toString();

        filename = bucketClient.get_csv(bucketName, recordKey, objectVersion);
        return RepeatStatus.FINISHED;

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        executionContext.put(FILENAME.name(), filename);

        return ExitStatus.COMPLETED;
    }
}
