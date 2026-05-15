package com.trycatchus.echoo.dtos.responses.auth;

import java.util.UUID;

public record AuthResponse(
    String token, 
    UUID userId

) {}