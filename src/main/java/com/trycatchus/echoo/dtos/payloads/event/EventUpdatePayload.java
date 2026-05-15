package com.trycatchus.echoo.dtos.payloads.event;

import java.time.LocalDateTime;

import com.trycatchus.echoo.enums.EventStatus;

public record EventUpdatePayload(
    String name,
    LocalDateTime startDate,
    LocalDateTime endDate,
    String description,
    Integer capacity,
    EventStatus eventStatus,
    String locationId,
    String organizerId

) {}