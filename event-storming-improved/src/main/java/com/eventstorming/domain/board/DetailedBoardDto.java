package com.eventstorming.domain.board;

import com.eventstorming.aggregate.AggregateDto;
import com.eventstorming.stickynote.StickyNoteDto;

import java.util.List;

public class DetailedBoardDto {
    private Long id;
    private String name;
    private String description;
    private List<AggregateDto> aggregates;
    private List<StickyNoteDto> stickyNotes;

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

    public List<AggregateDto> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<AggregateDto> aggregates) {
        this.aggregates = aggregates;
    }

    public List<StickyNoteDto> getStickyNotes() {
        return stickyNotes;
    }

    public void setStickyNotes(List<StickyNoteDto> stickyNotes) {
        this.stickyNotes = stickyNotes;
    }
}
