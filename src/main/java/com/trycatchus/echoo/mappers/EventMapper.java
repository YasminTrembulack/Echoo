package com.trycatchus.echoo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trycatchus.echoo.dtos.payloads.event.EventPayload;
import com.trycatchus.echoo.dtos.responses.EventResponse;
import com.trycatchus.echoo.models.Event;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventMapper extends BaseMapper<EventPayload, Event, EventResponse> { }