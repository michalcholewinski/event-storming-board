package com.eventstorming.stickynote;

import com.eventstorming.board.BoardEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class StickyNoteService {
    private final StickyNoteRepository stickyNoteRepository;

    public StickyNoteService(StickyNoteRepository stickyNoteRepository) {
        this.stickyNoteRepository = stickyNoteRepository;
    }

    public List<StickyNoteDto> getStickyNotes(Long boardId) {
        return mapStickyNotes(stickyNoteRepository.findAllByBoard_Id(boardId));
    }

    private List<StickyNoteDto> mapStickyNotes(List<StickyNoteEntity> entities) {
        return entities.stream().map(this::mapStickyNote).collect(toList());
    }

    private StickyNoteDto mapStickyNote(StickyNoteEntity entity) {
        StickyNoteDto dto = new StickyNoteDto();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setColor(entity.getColor());
        dto.setPosX(entity.getPosX());
        dto.setPosY(entity.getPosY());
        dto.setType(entity.getType().name());
        return dto;
    }

    public StickyNoteDto createStickyNote(BoardEntity boardEntity, StickyNoteDto stickyNote) {
        StickyNoteEntity entity = new StickyNoteEntity();
        entity.setColor(stickyNote.getColor());
        entity.setText(stickyNote.getText());
        entity.setBoard(boardEntity);
        entity.setPosX(stickyNote.getPosX());
        entity.setPosY(stickyNote.getPosY());
        entity.setType(StickyNoteType.getValue(stickyNote.getType()));
        return mapStickyNote(stickyNoteRepository.save(entity));
    }
}
