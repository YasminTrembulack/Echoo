package com.trycatchus.echoo.dtos.responses;

public record ErrorResponse(
    Integer statusCode, 
    String message

) {}