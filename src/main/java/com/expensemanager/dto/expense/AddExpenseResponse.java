package com.expensemanager.dto.expense;

import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class AddExpenseResponse {
    private String id;
    private String description;
    private BigDecimal amount;
    private OffsetDateTime expenseDateTime;
    private User user;
    private Category category;
}
