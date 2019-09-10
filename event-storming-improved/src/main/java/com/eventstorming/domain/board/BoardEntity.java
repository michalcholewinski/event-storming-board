package com.eventstorming.domain.board;

import com.eventstorming.domain.aggregate.AggregateEntity;
import com.eventstorming.domain.team.TeamEntity;
import com.eventstorming.domain.stickynote.StickyNoteEntity;
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

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public Set<AggregateEntity> getAggregates() {
        return aggregates;
    }

    public void setAggregates(Set<AggregateEntity> aggregates) {
        this.aggregates = aggregates;
    }

    public Set<StickyNoteEntity> getStickyNotes() {
        return stickyNotes;
    }

    public void setStickyNotes(Set<StickyNoteEntity> stickyNotes) {
        this.stickyNotes = stickyNotes;
    }
}
