package fr.gbeaugnier.demo.springbatch.component.step.reader;

import fr.gbeaugnier.demo.springbatch.business.dto.TeamUserDto;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;

import static fr.gbeaugnier.demo.springbatch.config.BatchName.READER_NAME;
import static fr.gbeaugnier.demo.springbatch.config.ParameterName.FILENAME;

@Slf4j
@Component
@StepScope
public class TeamUserReader extends FlatFileItemReader<TeamUserDto> implements ItemReadListener<TeamUserDto> {

    private final TeamUserLineMapper teamUserLineMapper;

    public TeamUserReader(TeamUserLineMapper teamUserLineMapper) {
        this.teamUserLineMapper = teamUserLineMapper;
        setName(READER_NAME.getName());
        setLinesToSkip(1);
    }

    @PostConstruct
    public void init() {
        setLineMapper(teamUserLineMapper);
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        Object filename = executionContext.get(FILENAME.name());
        assert filename != null;

        File file = new File(filename.toString());
        setResource(new FileSystemResource(file));
    }

    @Override
    public void beforeRead() {
        log.info("Before read");
    }

    @Override
    public void afterRead(@NotNull TeamUserDto item) {
        log.info("After read {}", item);
    }
}
