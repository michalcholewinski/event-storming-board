package com.eventstorming.domain.board;

import com.eventstorming.aggregate.AggregateDto;
import com.eventstorming.stickynote.StickyNoteDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards/{boardId}")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping
    public DetailedBoardDto getBoard(@PathVariable("boardId") Long boardId){
        return boardService.getBoard(boardId);
    }

    @PostMapping("/aggregates")
    public AggregateDto createAggregate(@PathVariable("boardId") Long boardId,
                                        @RequestBody AggregateDto aggregate){
        return boardService.createAggregate(boardId, aggregate);
    }

    @PostMapping("/sticky-notes")
    public StickyNoteDto createStickyNote(@PathVariable("boardId") Long boardId,
                                        @RequestBody StickyNoteDto stickyNote){
        return boardService.createStickyNote(boardId, stickyNote);
    }

}
