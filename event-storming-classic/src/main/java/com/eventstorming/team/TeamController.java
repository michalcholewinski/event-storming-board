package com.eventstorming.team;

import com.eventstorming.board.BoardDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }


    @GetMapping
    public Set<TeamDto> getTeams() {
        return teamService.getTeams();
    }

    @PostMapping
    public TeamDto createTeam(@RequestBody TeamDto team) {
        return teamService.createTeam(team);
    }

    @PostMapping("/{teamId}/boards")
    public BoardDto createBoard(@PathVariable("teamId") Long teamId, @RequestBody BoardDto boardDto){
        return teamService.createBoard(teamId, boardDto);
    }

    @GetMapping("/{teamId}/boards")
    public List<BoardDto> getBoards(@PathVariable("teamId") Long teamId){
        return teamService.getBoards(teamId);
    }



}
