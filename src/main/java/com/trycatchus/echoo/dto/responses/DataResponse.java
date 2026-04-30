package com.trycatchus.echoo.dto.responses;

public record DataResponse<T>(
    String message, 
    T data
) {}
