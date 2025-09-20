package com.expensemanager.dto.user.userRegister;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserRegisterResponse {
    private String id;
    private String name;
    private String email;
    private Instant createdAt;
}
