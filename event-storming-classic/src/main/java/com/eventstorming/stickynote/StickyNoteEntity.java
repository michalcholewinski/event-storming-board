package com.eventstorming.stickynote;

import com.eventstorming.aggregate.AggregateEntity;
import com.eventstorming.board.BoardEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "STICKY_NOTE")
public class StickyNoteEntity {

    @Id
    @GenericGenerator(
            name = "sticky_note_seq_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "STICKY_NOTE_SEQ")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sticky_note_seq_generator")
    private Long id;

    private UUID uuid = UUID.randomUUID();

    private String text;
    private Integer posX;
    private Integer posY;
    private String color;
    @Enumerated(STRING)
    private StickyNoteType type;

    @ManyToOne
    private AggregateEntity aggregate;

    @ManyToOne
    private BoardEntity board;
}
