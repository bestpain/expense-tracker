package com.expensemanager.dto.user;

public record UserResponse(
        String id,
        String email,
        String role
) {}
