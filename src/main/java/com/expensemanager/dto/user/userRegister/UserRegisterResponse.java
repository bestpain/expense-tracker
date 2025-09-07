package com.expensemanager.dto.user.userRegister;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserRegisterResponse {
    private String Id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
