package com.expensemanager.dto.user.userRegister;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class UserRegisterRequest {
    @NotNull(message = "Name should not be blank.")
    @Min(value = 4,message = "Name should be at least 4 characters.")
    private String name;

    @Email(message = "Invalid Email Address.")
    private String email;

    @Range(min = 6, max=31,message = "Password should be minimum 6 letters.")
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
