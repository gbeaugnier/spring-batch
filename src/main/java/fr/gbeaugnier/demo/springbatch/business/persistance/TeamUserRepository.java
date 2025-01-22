package fr.gbeaugnier.demo.springbatch.business.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamUserRepository extends CrudRepository<TeamUserEntity, Long> {
    Optional<TeamUserEntity> findByTeamId(String teamId);
}
