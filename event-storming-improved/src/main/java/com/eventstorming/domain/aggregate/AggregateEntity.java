package com.eventstorming.domain.aggregate;

import com.eventstorming.domain.board.BoardEntity;
import com.eventstorming.domain.stickynote.StickyNoteEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "AGGREGATE")
public class AggregateEntity {

    @Id
    @GenericGenerator(
            name = "aggregate_seq_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "AGGREGATE_SEQ")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aggregate_seq_generator")
    private Long id;

    private UUID uuid = UUID.randomUUID();

    private String name;
    private String description;

    @ManyToOne
    private BoardEntity board;

    @OneToMany(mappedBy = "aggregate")
    private Set<StickyNoteEntity> stickyNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BoardEntity getBoard() {
        return board;
    }

    public void setBoard(BoardEntity board) {
        this.board = board;
    }

    public Set<StickyNoteEntity> getStickyNotes() {
        return stickyNotes;
    }

    public void setStickyNotes(Set<StickyNoteEntity> stickyNotes) {
        this.stickyNotes = stickyNotes;
    }
}
