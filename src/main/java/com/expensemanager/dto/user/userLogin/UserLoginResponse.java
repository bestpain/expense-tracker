package com.expensemanager.dto.user.userLogin;

import lombok.Data;

@Data
public class UserLoginResponse {
    private String token;
    private long expiresIn;
}
