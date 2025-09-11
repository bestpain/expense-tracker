package com.expensemanager.dto.user.userLogin;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String email;
    private String password;
}
