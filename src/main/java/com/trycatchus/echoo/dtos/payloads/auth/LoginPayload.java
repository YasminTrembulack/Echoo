package com.trycatchus.echoo.dtos.payloads.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginPayload(
    @NotBlank(message = "Field 'email' is required")
    String email,

    @NotBlank(message = "Field 'password' is required")
    String password

) {}