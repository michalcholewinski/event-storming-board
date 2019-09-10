package com.eventstorming.domain.team;

import com.eventstorming.domain.board.BoardFacade;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TeamConfiguration {

    @Bean
    TeamFacade teamFacade(BoardFacade boardFacade, DSLContext dsl) {
        return new TeamFacade(new TeamService(boardFacade, dsl));
    }
}
