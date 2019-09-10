package com.eventstorming.domain.board;

import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BoardConfiguration {

    @Bean
    BoardFacade boardFacade(DSLContext dsl){
        AggregateService aggregateService = new AggregateService(dsl);
        StickyNoteService stickyNoteService = new StickyNoteService(dsl);
        BoardService boardService = new BoardService(aggregateService, stickyNoteService, dsl);
        return new BoardFacade(boardService);
    }
}
