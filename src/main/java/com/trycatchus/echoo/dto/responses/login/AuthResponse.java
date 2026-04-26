package com.trycatchus.echoo.dto.responses.login;

import java.util.UUID;

public record AuthResponse(
    String message,
    String token,
    UUID userId
)
{ }