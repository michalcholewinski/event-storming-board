package com.eventstorming.stickynote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StickyNoteRepository extends JpaRepository<StickyNoteEntity, Long> {
    List<StickyNoteEntity> findAllByBoard_Id(Long boardId);
}
