package com.trycatchus.echoo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trycatchus.echoo.model.Event;
import com.trycatchus.echoo.dto.payload.event.EventPayload;
import com.trycatchus.echoo.dto.responses.EventResponse;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventMapper extends BaseMapper<EventPayload, Event, EventResponse> { }