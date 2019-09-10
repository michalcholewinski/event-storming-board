package com.eventstorming.domain.aggregate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AggregateRepository extends JpaRepository<AggregateEntity, Long> {
    List<AggregateEntity> findAllByBoard_Id(Long boardId);
}
