package com.expensemanager.dto.user.userRegister;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserRegisterRequest {
    @NotBlank(message = "Name should not be blank.")
    @Size(min = 4, message = "Name should be at least 4 characters.")
    private String name;

    @NotBlank
    @Email(message = "Invalid Email Address.")
    private String email;

    @NotBlank
    @Size(min = 4, max = 31, message = "Password should be between 6 and 31 characters.")
    private String password;

    @JsonProperty("incomelimit")
    @Min(0)
    private BigDecimal incomeLimit;

    @Override
    public String toString() {
        return "UserRegisterRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
