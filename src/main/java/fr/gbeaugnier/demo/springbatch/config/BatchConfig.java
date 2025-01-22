package fr.gbeaugnier.demo.springbatch.config;

import fr.gbeaugnier.demo.springbatch.business.dto.TeamUserDto;
import fr.gbeaugnier.demo.springbatch.business.persistance.TeamUserEntity;
import fr.gbeaugnier.demo.springbatch.step.processor.TeamUserInsertProcessor;
import fr.gbeaugnier.demo.springbatch.step.processor.TeamUserUpdateProcessor;
import fr.gbeaugnier.demo.springbatch.step.reader.TeamUserReader;
import fr.gbeaugnier.demo.springbatch.step.tasklet.DeleteTemporaryFileTasklet;
import fr.gbeaugnier.demo.springbatch.step.tasklet.DownloadFileTasklet;
import fr.gbeaugnier.demo.springbatch.step.writer.TeamUserInsertWriter;
import fr.gbeaugnier.demo.springbatch.step.writer.TeamUserUpdateWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import static fr.gbeaugnier.demo.springbatch.config.BatchName.*;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {


    private final DownloadFileTasklet downloadFileTasklet;
    private final DeleteTemporaryFileTasklet deleteTemporaryFileTasklet;
    private final TeamUserReader teamUserReader;
    private final TeamUserInsertProcessor teamUserInsertProcessor;
    private final TeamUserUpdateProcessor teamUserUpdateProcessor;
    private final TeamUserInsertWriter teamUserInsertWriter;
    private final TeamUserUpdateWriter teamUserUpdateWriter;

    @Bean
    public Job updateTeamUserJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        Flow insertTeamUserFlow = new FlowBuilder<Flow>(INSERT_TEAM_USER_FLOW.getName())
                .from(insertTeamUserStep(jobRepository, transactionManager))
                .end();

        Flow updateTeamUserFlow = new FlowBuilder<Flow>(UPDATE_TEAM_USER_FLOW.getName())
                .from(updateTeamUserStep(jobRepository, transactionManager))
                .end();

        Flow parallelFlows = new FlowBuilder<Flow>(PARALLEL_FLOWS.getName())
                .split(new SimpleAsyncTaskExecutor())
                .add(insertTeamUserFlow, updateTeamUserFlow)
                .build();

        return new JobBuilder(UPDATE_TEAM_USER.getName(), jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(downloadFileToTemporaryFile(jobRepository, transactionManager))
                .next(parallelFlows)
                .next(deleteTemporaryFile(jobRepository, transactionManager))
                .end()
                .build();
    }

    @Bean
    public Flow downloadFileToTemporaryFile(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new FlowBuilder<Flow>(DOWNLOAD_FILE_FLOW.getName())
                .from(new StepBuilder(DOWNLOAD_FILE_TO_TEMPORARY_FILE.getName(), jobRepository)
                        .tasklet(downloadFileTasklet, transactionManager)
                        .build())
                .build();
    }

    @Bean
    public Step insertTeamUserStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(INSERT_TEAM_USER_STEP.getName(), jobRepository)
                .<TeamUserDto, TeamUserEntity>chunk(5, transactionManager)
                .reader(teamUserReader)
                .processor(teamUserInsertProcessor)
                .writer(teamUserInsertWriter)
                .build();
    }

    @Bean
    public Step updateTeamUserStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(UPDATE_TEAM_USER_STEP.getName(), jobRepository)
                .<TeamUserDto, TeamUserEntity>chunk(5, transactionManager)
                .reader(teamUserReader)
                .processor(teamUserUpdateProcessor)
                .writer(teamUserUpdateWriter)
                .build();
    }

    @Bean
    public Step deleteTemporaryFile(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder(DELETE_TEMPORARY_FILE_TASKLET.getName(), jobRepository)
                .tasklet(deleteTemporaryFileTasklet, transactionManager)
                .build();
    }

}
