package com.trycatchus.echoo.dto.payload.location;

public record LocationUpdatePayload(
    String establishment,
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String country,
    String postalCode

) {}
