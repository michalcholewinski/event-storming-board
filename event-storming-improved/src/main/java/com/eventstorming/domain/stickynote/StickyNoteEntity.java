package com.eventstorming.domain.stickynote;

import com.eventstorming.domain.aggregate.AggregateEntity;
import com.eventstorming.domain.board.BoardEntity;
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

    @Column(name="pos_x")
    private Integer posX;

    @Column(name="pos_y")
    private Integer posY;

    private String color;
    @Enumerated(STRING)
    private StickyNoteType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public StickyNoteType getType() {
        return type;
    }

    public void setType(StickyNoteType type) {
        this.type = type;
    }

    public AggregateEntity getAggregate() {
        return aggregate;
    }

    public void setAggregate(AggregateEntity aggregate) {
        this.aggregate = aggregate;
    }

    public BoardEntity getBoard() {
        return board;
    }

    public void setBoard(BoardEntity board) {
        this.board = board;
    }

    @ManyToOne
    private AggregateEntity aggregate;

    @ManyToOne
    private BoardEntity board;
}
