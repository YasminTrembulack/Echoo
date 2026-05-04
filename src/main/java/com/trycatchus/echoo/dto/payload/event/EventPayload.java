package com.trycatchus.echoo.dto.payload.event;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

import com.trycatchus.echoo.enums.EventStatus;

public record EventPayload(
    @NotBlank(message = "Field 'name' is required")
    String name,

    @NotBlank(message = "Field 'startDate' is required")
    LocalDateTime startDate,

    @NotBlank(message = "Field 'endDate' is required")
    LocalDateTime endDate,

    String description,
    Integer capacity,
    EventStatus eventStatus,
    String locationId,
    String organizerId

) {}