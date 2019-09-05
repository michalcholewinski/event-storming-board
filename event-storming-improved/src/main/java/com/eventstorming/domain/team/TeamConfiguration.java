package com.eventstorming.domain.team;

import com.eventstorming.domain.board.BoardDto;
import com.eventstorming.domain.team.dto.TeamDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static java.lang.Long.parseLong;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
class TeamConfiguration {

    private final TeamService teamService;

    TeamConfiguration(TeamService teamService) {
        this.teamService = teamService;
    }


    @Bean
    RouterFunction<ServerResponse> routing() {
        return route(GET("/teams"),
                req -> ok().body(teamService.getTeams(), TeamDto.class))
                .andRoute(POST("/teams"),
                        req -> req.body(toMono(TeamDto.class))
                                .doOnNext(teamService::createTeam)
                                .then(ok().build()))
                .andRoute(POST("/teams/{teamId}/boards"),
                        req -> req.body(toMono(BoardDto.class))
                                .doOnNext(x -> teamService.createBoard(
                                        parseLong(req.pathVariable("teamId")), x)
                                ).then(ok().build()));
    }
}
