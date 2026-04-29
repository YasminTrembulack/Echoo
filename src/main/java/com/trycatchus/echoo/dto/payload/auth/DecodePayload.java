package com.trycatchus.echoo.dto.payload.auth;

import jakarta.validation.constraints.NotBlank;

public record DecodePayload(

    @NotBlank(message = "Field 'token' is required")
    String token

)
{ }