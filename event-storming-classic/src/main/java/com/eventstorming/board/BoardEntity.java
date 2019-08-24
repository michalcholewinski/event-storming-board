package com.eventstorming.board;

import com.eventstorming.aggregate.AggregateEntity;
import com.eventstorming.stickynote.StickyNoteEntity;
import com.eventstorming.team.TeamEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "BOARD")
public class BoardEntity {

    @Id
    @GenericGenerator(
            name = "board_seq_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "BOARD_SEQ")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_generator")
    private Long id;

    private UUID uuid = UUID.randomUUID();

    private String name;
    private String description;

    @ManyToOne
    private TeamEntity team;

    @OneToMany(mappedBy = "board")
    private Set<AggregateEntity> aggregates;

    @OneToMany(mappedBy = "board")
    private Set<StickyNoteEntity> stickyNotes;
}
