package com.eventstorming.domain.team;

import co.cantina.spring.jooq.sample.model.Sequences;
import co.cantina.spring.jooq.sample.model.Tables;
import co.cantina.spring.jooq.sample.model.tables.records.TeamRecord;
import com.eventstorming.domain.board.BoardDto;
import com.eventstorming.domain.board.BoardService;
import com.eventstorming.domain.team.dto.TeamDto;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static co.cantina.spring.jooq.sample.model.Tables.BOARD;
import static co.cantina.spring.jooq.sample.model.Tables.TEAM;
import static java.util.UUID.randomUUID;

@Service
class TeamService {
    private final TeamRepository teamRepository;
    private final BoardService boardService;
    private final DSLContext dsl;

    public TeamService(TeamRepository teamRepository, BoardService boardService, DSLContext dsl) {
        this.teamRepository = teamRepository;
//        this.teamRepository = teamRepository;
        this.boardService = boardService;
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
        return boardService.createBoard(boardDto, teamId);
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
        return boardService.getBoards(teamId);
    }
}
