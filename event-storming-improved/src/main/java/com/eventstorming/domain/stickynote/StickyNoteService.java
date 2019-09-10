package com.eventstorming.domain.stickynote;

import co.cantina.spring.jooq.sample.model.Sequences;
import co.cantina.spring.jooq.sample.model.tables.records.StickyNoteRecord;
import com.eventstorming.domain.stickynote.StickyNoteDto;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static co.cantina.spring.jooq.sample.model.Tables.STICKY_NOTE;
import static java.util.stream.Collectors.toList;

@Service
public class StickyNoteService {
    private final DSLContext dsl;

    public StickyNoteService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<StickyNoteDto> getStickyNotes(Long boardId) {
        return dsl.selectFrom(STICKY_NOTE)
                .where(STICKY_NOTE.BOARD_ID.eq(boardId))
                .fetch()
                .stream()
                .map(this::mapStickyNote)
                .collect(toList());
    }

    private StickyNoteDto mapStickyNote(StickyNoteRecord record) {
        StickyNoteDto dto = new StickyNoteDto();
        dto.setId(record.getId());
        dto.setText(record.getText());
        dto.setColor(record.getColor());
        dto.setPosX(record.getPosX().intValue());
        dto.setPosY(record.getPosY().intValue());
        dto.setType(record.getType());
        return dto;
    }

    public StickyNoteDto createStickyNote(Long boardId, StickyNoteDto stickyNote) {
        StickyNoteRecord stickyNoteRecord = dsl.insertInto(STICKY_NOTE,
                STICKY_NOTE.ID,
                STICKY_NOTE.COLOR,
                STICKY_NOTE.TEXT,
                STICKY_NOTE.POS_X,
                STICKY_NOTE.POS_Y,
                STICKY_NOTE.TYPE,
                STICKY_NOTE.BOARD_ID,
                STICKY_NOTE.UUID)
                .values(dsl.nextval(Sequences.STICKY_NOTE_SEQ),
                        stickyNote.getColor(),
                        stickyNote.getText(),
                        new BigDecimal(stickyNote.getPosX()),
                        new BigDecimal(stickyNote.getPosY()),
                        stickyNote.getType(),
                        boardId,
                        UUID.randomUUID())
                .returning()
                .fetchOne();

        return mapStickyNote(stickyNoteRecord);
    }
}
