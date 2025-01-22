package fr.gbeaugnier.demo.springbatch.business.service;

import fr.gbeaugnier.demo.springbatch.business.dto.TeamUserDto;
import fr.gbeaugnier.demo.springbatch.business.persistance.TeamUserEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TeamUserMapper {

    @Mapping(target = "id", ignore = true)
    TeamUserEntity toEntity(TeamUserDto teamUserDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(TeamUserDto dto, @MappingTarget TeamUserEntity entity);

}
