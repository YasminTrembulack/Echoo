package com.trycatchus.echoo.dto.responses.login;

import java.util.UUID;

public record AuthResponse(String token, UUID userId)
{ }