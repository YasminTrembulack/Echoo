package com.trycatchus.echoo.dto.responses;

public sealed interface SimpleMessageResponse {
    public record Ok(String message) implements SimpleMessageResponse { };
    public record Error(Integer errorCode, String message) implements SimpleMessageResponse { };
}
