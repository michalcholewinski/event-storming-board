package com.eventstorming.infrastructure.server;

import com.eventstorming.aggregate.AggregateDto;
import com.eventstorming.domain.board.BoardDto;
import com.eventstorming.domain.board.BoardService;
import com.eventstorming.domain.board.DetailedBoardDto;
import com.eventstorming.domain.team.TeamService;
import com.eventstorming.domain.team.dto.TeamDto;
import com.eventstorming.stickynote.StickyNoteDto;
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
class Server {

    private final BoardService boardService;
    private final TeamService teamService;

    Server(BoardService boardService, TeamService teamService) {
        this.boardService = boardService;
        this.teamService = teamService;
    }

    @Bean
    RouterFunction<ServerResponse> teamRouting() {
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
                                ).then(ok().build()))
                .andRoute(GET("/teams/{teamId}/boards"),
                        req -> ok().body(teamService.getBoards(
                                parseLong(req.pathVariable("teamId"))), BoardDto.class))
                .andRoute(GET("/boards/{boardId}"),
                        req -> ok().body(
                                boardService.getBoard(
                                        parseLong(req.pathVariable("boardId"))
                                ), DetailedBoardDto.class))
                .andRoute(POST("/boards/{boardId}/aggregates"),
                        req -> req.body(toMono(AggregateDto.class))
                                .doOnNext(body -> boardService.createAggregate(
                                        parseLong(req.pathVariable("boardId")), body
                                ))
                                .then(ok().build()))
                .andRoute(POST("/boards/{boardId}/sticky-notes"),
                        req -> req.body(toMono(StickyNoteDto.class))
                                .doOnNext(body -> boardService.createStickyNote(
                                        parseLong(req.pathVariable("boardId")), body
                                ))
                                .then(ok().build()));
    }
}
