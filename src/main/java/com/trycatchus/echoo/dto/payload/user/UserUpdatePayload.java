package com.trycatchus.echoo.dto.payload.user;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import com.trycatchus.echoo.enums.UserRole;

import jakarta.validation.constraints.Email;

public record UserUpdatePayload(
        String firstName,
        String lastName,
        String username,
        String cpf,
        LocalDate birthDate,
        UserRole userRole,

        @Email(message = "Invalid email")
        String email,

        @Size(min = 8, message = "Field 'password' must have at least 8 characters")
        String password

) {}