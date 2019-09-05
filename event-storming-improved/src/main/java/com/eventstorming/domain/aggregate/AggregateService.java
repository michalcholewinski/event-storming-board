package com.eventstorming.aggregate;

import com.eventstorming.domain.board.BoardEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AggregateService {
    private final AggregateRepository aggregateRepository;

    public AggregateService(AggregateRepository aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
    }

    public List<AggregateDto> getAggregates(Long boardId) {
        return mapAggregates(aggregateRepository.findAllByBoard_Id(boardId));
    }

    private List<AggregateDto> mapAggregates(List<AggregateEntity> entities) {
        return entities.stream().map(this::mapAggregate).collect(toList());
    }

    private AggregateDto mapAggregate(AggregateEntity entity) {
        AggregateDto dto = new AggregateDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public AggregateDto createAggregate(BoardEntity boardEntity, AggregateDto aggregate) {
        AggregateEntity entity = new AggregateEntity();
        entity.setBoard(boardEntity);
        entity.setDescription(aggregate.getDescription());
        entity.setName(aggregate.getName());
        return mapAggregate(aggregateRepository.save(entity));
    }
}
