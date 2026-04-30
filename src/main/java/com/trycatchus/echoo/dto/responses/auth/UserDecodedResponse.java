package com.trycatchus.echoo.dto.responses.auth;

public record UserDecodedResponse(
    String userId,
    String firstName,
    String username,
    String role
) {}
