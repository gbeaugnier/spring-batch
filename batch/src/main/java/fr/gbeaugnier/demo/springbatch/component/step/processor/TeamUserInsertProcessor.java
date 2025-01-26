package fr.gbeaugnier.demo.springbatch.component.step.processor;

import fr.gbeaugnier.demo.springbatch.business.dto.TeamUserDto;
import fr.gbeaugnier.demo.springbatch.business.persistance.TeamUserEntity;
import fr.gbeaugnier.demo.springbatch.business.service.TeamUserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamUserInsertProcessor implements ItemProcessor<TeamUserDto, TeamUserEntity> {

    private final TeamUserService teamUserService;

    @Override
    public TeamUserEntity process(@NotNull TeamUserDto item) {
        return teamUserService.insertTeamUser(item);
    }

}
