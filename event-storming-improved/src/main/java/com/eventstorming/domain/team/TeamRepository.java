package com.eventstorming.domain.team;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends Repository<TeamEntity, Long> {
    List<TeamEntity> findAll();

    TeamEntity save(TeamEntity newTeam);

    Optional<TeamEntity> findById(Long teamId);
}
