package com.eventstorming.domain.team;

import com.eventstorming.domain.board.BoardDto;
import com.eventstorming.domain.team.dto.TeamDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
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
