package com.eventstorming.domain.board;

import com.eventstorming.aggregate.AggregateDto;
import com.eventstorming.domain.team.TeamEntity;
import com.eventstorming.stickynote.StickyNoteDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BoardFacade {
    private final BoardService boardService;

    public BoardFacade(BoardService boardService) {
        this.boardService = boardService;
    }

    public BoardDto createBoard(BoardDto boardDto, TeamEntity teamEntity) {
        return boardService.createBoard(boardDto, teamEntity);
    }

    public List<BoardDto> getBoards(Long teamId) {
        return boardService.getBoards(teamId);
    }

    public Mono<DetailedBoardDto> getBoard(Long boardId) {
        return Mono.fromCallable(() -> boardService.getBoard(boardId));
    }

    public AggregateDto createAggregate(Long boardId, AggregateDto aggregate) {
        return boardService.createAggregate(boardId, aggregate);
    }

    public StickyNoteDto createStickyNote(Long boardId, StickyNoteDto stickyNote) {
        return boardService.createStickyNote(boardId, stickyNote);
    }
}
