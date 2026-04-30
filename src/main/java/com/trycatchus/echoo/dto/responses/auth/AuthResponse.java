package com.trycatchus.echoo.dto.responses.auth;

import java.util.UUID;

public record AuthResponse(
    String token, 
    UUID userId

) {}