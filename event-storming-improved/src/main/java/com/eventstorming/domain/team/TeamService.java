package com.eventstorming.domain.team;

import com.eventstorming.domain.board.BoardDto;
import com.eventstorming.domain.board.BoardService;
import com.eventstorming.domain.team.dto.TeamDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
class TeamService {
    private final TeamRepository teamRepository;
    private final BoardService boardService;

    public TeamService(TeamRepository teamRepository, BoardService boardService) {
        this.teamRepository = teamRepository;
        this.boardService = boardService;
    }

    public Set<TeamDto> getTeams() {
        return mapTeams(teamRepository.findAll());
    }

    public TeamDto createTeam(TeamDto in) {
        TeamEntity newTeam = new TeamEntity();
        newTeam.setName(in.getName());
        newTeam.setDescription(in.getDescription());

        return mapTeam(teamRepository.save(newTeam));
    }

    private Set<TeamDto> mapTeams(List<TeamEntity> teams) {
        return teams.stream().map(this::mapTeam).collect(toSet());
    }

    private TeamDto mapTeam(TeamEntity team) {
        TeamDto teamDto = new TeamDto();
        teamDto.setId(team.getId());
        teamDto.setName(team.getName());
        teamDto.setDescription(team.getDescription());
        return teamDto;
    }

    public BoardDto createBoard(Long teamId, BoardDto boardDto) {
        TeamEntity teamEntity = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        return boardService.createBoard(boardDto, teamEntity);
    }

    public List<BoardDto> getBoards(Long teamId) {
        teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        return boardService.getBoards(teamId);
    }
}
