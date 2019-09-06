package com.eventstorming.infrastructure.server;

import com.eventstorming.aggregate.AggregateDto;
import com.eventstorming.domain.board.BoardDto;
import com.eventstorming.domain.board.BoardFacade;
import com.eventstorming.domain.board.DetailedBoardDto;
import com.eventstorming.domain.team.TeamFacade;
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

    private final BoardFacade boardFacade;
    private final TeamFacade teamFacade;

    Server(BoardFacade boardFacade, TeamFacade teamFacade) {
        this.boardFacade = boardFacade;
        this.teamFacade = teamFacade;
    }

    @Bean
    RouterFunction<ServerResponse> teamRouting() {
        return route(GET("/teams"),
                req -> ok().body(teamFacade.getTeams(), TeamDto.class))
                .andRoute(POST("/teams"),
                        req -> req.body(toMono(TeamDto.class))
                                .doOnNext(teamFacade::createTeam)
                                .then(ok().build()))
                .andRoute(POST("/teams/{teamId}/boards"),
                        req -> req.body(toMono(BoardDto.class))
                                .doOnNext(x -> teamFacade.createBoard(
                                        parseLong(req.pathVariable("teamId")), x)
                                ).then(ok().build()))
                .andRoute(GET("/teams/{teamId}/boards"),
                        req -> ok().body(teamFacade.getBoards(
                                parseLong(req.pathVariable("teamId"))), BoardDto.class))
                .andRoute(GET("/boards/{boardId}"),
                        req -> ok().body(
                                boardFacade.getBoard(
                                        parseLong(req.pathVariable("boardId"))
                                ), DetailedBoardDto.class))
                .andRoute(POST("/boards/{boardId}/aggregates"),
                        req -> req.body(toMono(AggregateDto.class))
                                .doOnNext(body -> boardFacade.createAggregate(
                                        parseLong(req.pathVariable("boardId")), body
                                ))
                                .then(ok().build()))
                .andRoute(POST("/boards/{boardId}/sticky-notes"),
                        req -> req.body(toMono(StickyNoteDto.class))
                                .doOnNext(body -> boardFacade.createStickyNote(
                                        parseLong(req.pathVariable("boardId")), body
                                ))
                                .then(ok().build()));
    }
}
