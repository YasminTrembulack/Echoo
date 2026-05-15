package com.trycatchus.echoo.dtos.responses.auth;

public record UserDecodedResponse(
    String userId,
    String firstName,
    String username,
    String role

) {}
