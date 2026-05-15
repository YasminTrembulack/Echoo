package com.trycatchus.echoo.dtos.responses;

public record DataResponse<T>(
    String message, 
    T data

) {}
