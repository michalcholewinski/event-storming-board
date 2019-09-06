package com.eventstorming.domain.board;

import com.eventstorming.aggregate.AggregateDto;
import com.eventstorming.aggregate.AggregateService;
import com.eventstorming.domain.stickynote.StickyNoteService;
import com.eventstorming.domain.team.TeamEntity;
import com.eventstorming.stickynote.StickyNoteDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final AggregateService aggregateService;
    private final StickyNoteService stickyNoteService;

    public BoardService(BoardRepository boardRepository, AggregateService aggregateService, StickyNoteService stickyNoteService) {
        this.boardRepository = boardRepository;
        this.aggregateService = aggregateService;
        this.stickyNoteService = stickyNoteService;
    }

    public BoardDto createBoard(BoardDto boardDto, TeamEntity teamEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setName(boardDto.getName());
        boardEntity.setDescription(boardDto.getDescription());
        boardEntity.setTeam(teamEntity);
        return mapBoard(boardRepository.save(boardEntity));
    }

    public List<BoardDto> getBoards(Long teamId) {
        return mapBoards(boardRepository.findAllByTeam_Id(teamId));
    }

    private List<BoardDto> mapBoards(List<BoardEntity> entities) {
        return entities.stream().map(this::mapBoard).collect(toList());
    }

    private BoardDto mapBoard(BoardEntity entity) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(entity.getId());
        boardDto.setName(entity.getName());
        boardDto.setDescription(entity.getDescription());
        return boardDto;
    }

    public Mono<DetailedBoardDto> getBoard(Long boardId) {
        return Mono.fromCallable(() -> toDetailedBoard(boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new)));
    }

    private DetailedBoardDto toDetailedBoard(BoardEntity entity) {
        DetailedBoardDto dto = new DetailedBoardDto();
        Long id = entity.getId();
        dto.setId(id);
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setAggregates(aggregateService.getAggregates(id));
        dto.setStickyNotes(stickyNoteService.getStickyNotes(id));
        return dto;
    }

    public AggregateDto createAggregate(Long boardId, AggregateDto aggregate) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return aggregateService.createAggregate(boardEntity, aggregate);
    }

    public StickyNoteDto createStickyNote(Long boardId, StickyNoteDto stickyNote) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return stickyNoteService.createStickyNote(boardEntity, stickyNote);
    }
}
