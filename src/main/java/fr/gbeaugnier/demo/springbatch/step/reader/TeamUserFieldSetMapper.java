package fr.gbeaugnier.demo.springbatch.step.reader;

import fr.gbeaugnier.demo.springbatch.business.dto.TeamUserDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class TeamUserFieldSetMapper implements FieldSetMapper<TeamUserDto> {

    @NotNull
    @Override
    public TeamUserDto mapFieldSet(FieldSet fieldSet) {
        return new TeamUserDto(
                fieldSet.readString(0),
                fieldSet.readString(1),
                fieldSet.readString(2)
        );
    }

}
