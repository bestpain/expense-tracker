package com.expensemanager.dto.user.userProfileUpdate;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class UpdateProfileRequest {
    @Size(min = 4, message = "Name should be at least 4 characters.")
    private String name;
    @Email(message = "Invalid Email Address.")
    private String email;
    @Size(min = 6, max = 31, message = "Password should be between 6 and 31 characters.")
    private String password;
    @Min(0)
    private BigDecimal incomeLimit;
}