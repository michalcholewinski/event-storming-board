package com.eventstorming.domain.board;

import com.eventstorming.domain.board.dto.AggregateDto;
import com.eventstorming.domain.board.dto.BoardDto;
import com.eventstorming.domain.board.dto.DetailedBoardDto;
import com.eventstorming.domain.board.dto.StickyNoteDto;
import reactor.core.publisher.Mono;

import java.util.List;

public class BoardFacade {
    private final BoardService boardService;

    public BoardFacade(BoardService boardService) {
        this.boardService = boardService;
    }

    public BoardDto createBoard(BoardDto boardDto, Long teamId) {
        return boardService.createBoard(boardDto, teamId);
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
