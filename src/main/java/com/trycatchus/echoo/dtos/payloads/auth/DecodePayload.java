package com.trycatchus.echoo.dtos.payloads.auth;

import jakarta.validation.constraints.NotBlank;

public record DecodePayload(
    @NotBlank(message = "Field 'token' is required")
    String token

) {}