package com.trycatchus.echoo.dto.payload.theme;

import jakarta.validation.constraints.NotBlank;


public record ThemePayload(
        @NotBlank(message = "Field 'name' is required")
        String name,
        String descriprion

) {}