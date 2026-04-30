package com.trycatchus.echoo.dto.payload.location;

import jakarta.validation.constraints.NotBlank;

public record LocationPayload(
    String establishment,

    @NotBlank(message = "Field 'street' is required")
    String street,

    String number,

    String complement,

    @NotBlank(message = "Field 'neighborhood' is required")
    String neighborhood,

    @NotBlank(message = "Field 'city' is required")
    String city,

    @NotBlank(message = "Field 'state' is required")
    String state,

    @NotBlank(message = "Field 'country' is required")
    String country,

    @NotBlank(message = "Field 'postalCode' is required")
    String postalCode

) {}
