package com.expensemanager.dto.expense;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AddExpenseRequest {
    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 100, message = "Description must be at most 100 characters")
    private String description;

    @NotNull(message = "Category Id can not be null")
    private String categoryId;
}
