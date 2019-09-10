package com.eventstorming.domain.team;

import com.eventstorming.domain.board.dto.BoardDto;
import com.eventstorming.domain.team.dto.TeamDto;
import reactor.core.publisher.Flux;

public class TeamFacade {
    private final TeamService teamService;

    public TeamFacade(TeamService teamService) {
        this.teamService = teamService;
    }

    public Flux<TeamDto> getTeams() {
        return Flux.fromIterable(teamService.getTeams());
    }

    public TeamDto createTeam(TeamDto in) {
        return teamService.createTeam(in);
    }

    public BoardDto createBoard(Long teamId, BoardDto boardDto) {
        return teamService.createBoard(teamId, boardDto);
    }

    public Flux<BoardDto> getBoards(Long teamId) {
        return Flux.fromIterable(teamService.getBoards(teamId));
    }
}
