package com.eventstorming.domain.board;

import co.cantina.spring.jooq.sample.model.tables.records.BoardRecord;
import com.eventstorming.domain.board.dto.*;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static co.cantina.spring.jooq.sample.model.Sequences.BOARD_SEQ;
import static co.cantina.spring.jooq.sample.model.Tables.BOARD;
import static java.util.stream.Collectors.toList;

class BoardService {
    private final AggregateService aggregateService;
    private final StickyNoteService stickyNoteService;
    private final DSLContext dsl;

    public BoardService(AggregateService aggregateService, StickyNoteService stickyNoteService, DSLContext dsl) {
        this.aggregateService = aggregateService;
        this.stickyNoteService = stickyNoteService;
        this.dsl = dsl;
    }

    public BoardDto createBoard(BoardDto boardDto, Long teamId) {
        BoardRecord boardRecord = dsl.insertInto(BOARD, BOARD.ID, BOARD.NAME, BOARD.DESCRIPTION, BOARD.TEAM_ID, BOARD.UUID)
                .values(dsl.nextval(BOARD_SEQ), boardDto.getName(), boardDto.getDescription(), teamId, UUID.randomUUID())
                .returning()
                .fetchOne();
        return mapBoard(boardRecord);
    }

    public List<BoardDto> getBoards(Long teamId) {
        return dsl.selectFrom(BOARD)
                .where(BOARD.TEAM_ID.eq(teamId))
                .fetch()
                .stream().map(this::mapBoard)
                .collect(toList());
    }

    private BoardDto mapBoard(BoardRecord record) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(record.getId());
        boardDto.setName(record.getName());
        boardDto.setDescription(record.getDescription());
        return boardDto;
    }

    public DetailedBoardDto getBoard(Long boardId) {
        BoardRecord boardRecord = fetchBoard(boardId);
        return toDetailedBoard(boardRecord);
    }

    private BoardRecord fetchBoard(Long boardId) {
        return dsl.selectFrom(BOARD)
                .where(BOARD.ID.eq(boardId))
                .fetchOptional().orElseThrow(BoardNotFoundException::new);
    }

    private DetailedBoardDto toDetailedBoard(BoardRecord record) {
        DetailedBoardDto dto = new DetailedBoardDto();
        Long id = record.getId();
        dto.setId(id);
        dto.setName(record.getName());
        dto.setDescription(record.getDescription());
        dto.setAggregates(aggregateService.getAggregates(id));
        dto.setStickyNotes(stickyNoteService.getStickyNotes(id));
        return dto;
    }

    public AggregateDto createAggregate(Long boardId, AggregateDto aggregate) {
        fetchBoard(boardId);
        return aggregateService.createAggregate(boardId, aggregate);
    }

    public StickyNoteDto createStickyNote(Long boardId, StickyNoteDto stickyNote) {
        fetchBoard(boardId);
        return stickyNoteService.createStickyNote(boardId, stickyNote);
    }
}
