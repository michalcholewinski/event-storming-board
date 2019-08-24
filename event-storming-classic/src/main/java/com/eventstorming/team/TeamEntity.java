package com.eventstorming.team;

import com.eventstorming.board.BoardEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TEAM")
public class TeamEntity {

    @Id
    @GenericGenerator(
            name = "team_seq_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "TEAM_SEQ")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq_generator")
    private Long id;

    private UUID uuid = UUID.randomUUID();

    private String name;
    private String description;

    @OneToMany(mappedBy = "team")
    private Set<BoardEntity> boards;

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

    public Set<BoardEntity> getBoards() {
        return boards;
    }

    public void setBoards(Set<BoardEntity> boards) {
        this.boards = boards;
    }
}
