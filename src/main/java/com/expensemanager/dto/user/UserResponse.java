package com.expensemanager.dto.user;

import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
public class UserResponse {
    private String id;
    private String email;
    private String name;


    private BigDecimal incomeLimit;

    public UserResponse(String id, String email, String name, BigDecimal incomeLimit) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.incomeLimit = incomeLimit;
    }

}
