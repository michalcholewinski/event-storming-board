package com.eventstorming.domain.aggregate;

import co.cantina.spring.jooq.sample.model.tables.records.AggregateRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;

import static co.cantina.spring.jooq.sample.model.Sequences.AGGREGATE_SEQ;
import static co.cantina.spring.jooq.sample.model.Tables.AGGREGATE;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

@Service
public class AggregateService {
    private final AggregateRepository aggregateRepository;
    private final DSLContext dsl;

    public AggregateService(AggregateRepository aggregateRepository, DSLContext dsl) {
        this.aggregateRepository = aggregateRepository;
        this.dsl = dsl;
    }

    public List<AggregateDto> getAggregates(Long boardId) {
        return dsl.selectFrom(AGGREGATE)
                .where(AGGREGATE.BOARD_ID.eq(boardId))
                .fetch()
                .stream()
                .map(this::mapAggregate)
                .collect(toList());
    }


    private AggregateDto mapAggregate(AggregateRecord record) {
        AggregateDto dto = new AggregateDto();
        dto.setId(record.getId());
        dto.setName(record.getName());
        dto.setDescription(record.getDescription());
        return dto;
    }

    public AggregateDto createAggregate(Long boardId, AggregateDto aggregate) {
        AggregateRecord aggregateRecord = dsl.insertInto(AGGREGATE,
                AGGREGATE.ID, AGGREGATE.NAME, AGGREGATE.DESCRIPTION, AGGREGATE.BOARD_ID, AGGREGATE.UUID)
                .values(dsl.nextval(AGGREGATE_SEQ), aggregate.getName(), aggregate.getDescription(), boardId, randomUUID())
                .returning()
                .fetchOne();
        return mapAggregate(aggregateRecord);
    }
}
