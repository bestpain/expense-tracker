package com.expensemanager.dto.expense;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AddExpenseRequest(
        @NotNull(message = "Amount is required.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
        BigDecimal amount,

        @NotBlank(message="Description cannot be empty")
        @Size(max = 100, message = "Description must be at most 100 characters")
        String description,

        OffsetDateTime expenseDateTime
        ){}
