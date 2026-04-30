package com.trycatchus.echoo.dto.responses;

public record LocationResponse(
    String id,
    String establishment,
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String country,
    String postalCode,
    String createdAt,
    String updatedAt

) {}
