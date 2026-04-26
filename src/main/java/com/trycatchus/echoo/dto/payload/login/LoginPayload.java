package com.trycatchus.echoo.dto.payload.login;

public record LoginPayload(
    String email,
    String password
)
{ }