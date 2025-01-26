package fr.gbeaugnier.demo.springbatch.business.service;

import fr.gbeaugnier.demo.springbatch.business.dto.TeamUserDto;
import fr.gbeaugnier.demo.springbatch.business.persistance.TeamUserEntity;
import fr.gbeaugnier.demo.springbatch.business.persistance.TeamUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamUserService {

    private final TeamUserRepository teamUserRepository;
    private final TeamUserMapper teamUserMapper;

    public TeamUserEntity insertTeamUser(TeamUserDto teamUserDto) {
        var present = teamUserRepository
                .findByTeamId(teamUserDto.teamId())
                .isPresent();
        if (present) { return null; }
        return teamUserMapper.toEntity(teamUserDto);
    }

    public TeamUserEntity updateTeamUser(TeamUserDto teamUserDto) {
        var entity = teamUserRepository
                .findByTeamId(teamUserDto.teamId())
                .orElse(null);

        if (entity == null) { return null; }
        teamUserMapper.updateEntity(teamUserDto, entity);
        return entity;
    }

}
