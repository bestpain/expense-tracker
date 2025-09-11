package com.expensemanager.dto.user.userRegister;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UserRegisterRequest {
    @NotBlank(message = "Name should not be blank.")
    @Size(min = 4, message = "Name should be at least 4 characters.")
    private String name;

    @NotBlank
    @Email(message = "Invalid Email Address.")
    private String email;

    @NotBlank
    @Size(min = 6, max = 31, message = "Password should be between 6 and 31 characters.")
    private String password;

    @Override
    public String toString() {
        return "UserRegisterRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
