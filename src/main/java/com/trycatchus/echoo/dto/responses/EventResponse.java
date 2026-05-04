package com.trycatchus.echoo.dto.responses;

public record EventResponse(
    String id,
    String name,
    String description,
    Integer capacity,
    String startDate,
    String endDate,
    String eventStatus,
    LocationResponse location,
    UserResponse organizer,
    String createdAt,
    String updatedAt

) {}
