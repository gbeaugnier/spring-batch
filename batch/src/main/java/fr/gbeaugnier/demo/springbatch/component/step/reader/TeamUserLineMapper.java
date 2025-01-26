package fr.gbeaugnier.demo.springbatch.component.step.reader;

import fr.gbeaugnier.demo.springbatch.business.dto.TeamUserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TeamUserLineMapper extends DefaultLineMapper<TeamUserDto> {

    @Value("${header.names}")
    private String names;

    @Value("${line.delimiter}")
    private String delimiter;

    public TeamUserLineMapper() {
        final TeamUserFieldSetMapper fieldSetMapper = new TeamUserFieldSetMapper();
        setFieldSetMapper(fieldSetMapper);
    }

    @PostConstruct
    public void init() {
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(delimiter);
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(names.split(delimiter));

        setLineTokenizer(lineTokenizer);
    }

}
