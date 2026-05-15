package com.trycatchus.echoo.dtos.payloads.location;

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
