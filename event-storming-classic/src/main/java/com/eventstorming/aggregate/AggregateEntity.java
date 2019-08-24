package com.eventstorming.aggregate;

import com.eventstorming.board.BoardEntity;
import com.eventstorming.stickynote.StickyNoteEntity;
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
}
