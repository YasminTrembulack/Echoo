package com.trycatchus.echoo.dto.responses;

public record ErrorResponse(
    Integer statusCode, 
    String message
) {}