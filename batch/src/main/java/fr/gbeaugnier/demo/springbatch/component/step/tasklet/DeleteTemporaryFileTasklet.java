package fr.gbeaugnier.demo.springbatch.component.step.tasklet;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

import static fr.gbeaugnier.demo.springbatch.config.ParameterName.FILENAME;

@Component
public class DeleteTemporaryFileTasklet implements Tasklet, StepExecutionListener {

    private String filename;

    @Override
    public RepeatStatus execute(@NotNull StepContribution contribution, @NotNull ChunkContext chunkContext) {
        boolean delete = new File(filename).delete();
        return RepeatStatus.continueIf(!delete);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        filename = Objects.requireNonNull(executionContext.get(FILENAME.name())).toString();
    }

}
