package com.trycatchus.echoo.dto.responses;

import java.util.List;

public sealed interface DataResponse<T> {
    public record Ok<T>(String message, List<T> data) implements DataResponse<T> { };
    public record Error<T>(Integer errorCode, String message) implements DataResponse<T> { };
}

