package com.trycatchus.echoo.dto.responses;

public record ThemeResponse(
    String id,
    String name,
    String description,
    String createdAt,
    String updatedAt

) {}
