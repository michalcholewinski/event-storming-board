package com.eventstorming.domain.team;

import co.cantina.spring.jooq.sample.model.Sequences;
import co.cantina.spring.jooq.sample.model.tables.records.TeamRecord;
import com.eventstorming.domain.board.BoardFacade;
import com.eventstorming.domain.board.dto.BoardDto;
import com.eventstorming.domain.team.dto.TeamDto;
import com.eventstorming.domain.team.dto.TeamNotFoundException;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static co.cantina.spring.jooq.sample.model.Tables.TEAM;
import static java.util.UUID.randomUUID;

class TeamService {
    private final BoardFacade boardFacade;
    private final DSLContext dsl;

    public TeamService(BoardFacade boardFacade, DSLContext dsl) {
        this.boardFacade = boardFacade;
        this.dsl = dsl;
    }

    public Set<TeamDto> getTeams() {
        return dsl.selectFrom(TEAM)
                .fetch()
                .stream()
                .map(this::mapTeam)
                .collect(Collectors.toSet());
    }

    public TeamDto createTeam(TeamDto in) {
        TeamRecord teamRecord = dsl.insertInto(TEAM, TEAM.ID, TEAM.NAME, TEAM.DESCRIPTION, TEAM.UUID)
                .values(dsl.nextval(Sequences.TEAM_SEQ), in.getName(), in.getDescription(), randomUUID())
                .returning().fetchOne();
        return mapTeam(teamRecord);
    }

    private TeamDto mapTeam(TeamRecord team) {
        TeamDto teamDto = new TeamDto();
        teamDto.setId(team.getId());
        teamDto.setName(team.getName());
        teamDto.setDescription(team.getDescription());
        return teamDto;
    }

    public BoardDto createBoard(Long teamId, BoardDto boardDto) {
        if (!teamExists(teamId)) {
            throw new TeamNotFoundException();
        }
        return boardFacade.createBoard(boardDto, teamId);
    }

    private boolean teamExists(Long teamId) {
        return dsl.selectFrom(TEAM)
                .where(TEAM.ID.eq(teamId))
                .fetchCount() > 0;
    }

    public List<BoardDto> getBoards(Long teamId) {
        if (!teamExists(teamId)) {
            throw new TeamNotFoundException();
        }
        return boardFacade.getBoards(teamId);
    }
}