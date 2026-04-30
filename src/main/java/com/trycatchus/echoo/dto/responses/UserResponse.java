package com.trycatchus.echoo.dto.responses;

public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String username,
        String cpf,
        String birthDate,
        String email,
        String userRole,
        String createdAt,
        String updatedAt

) {}