package com.trycatchus.echoo.dtos.responses;

public record ThemeResponse(
    String id,
    String name,
    String description,
    String createdAt,
    String updatedAt

) {}
