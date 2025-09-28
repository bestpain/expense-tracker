package com.expensemanager.dto.expense;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record EditExpenseRequest(
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be positive")
        BigDecimal amount,

        @Size(min = 6, max = 100, message = "Description must be at most 100 characters")
        String description,

        OffsetDateTime expenseDateTime,

        @Size(min = 36, message = "CategoryId must be a valid UUID")
        String categoryId
) {
}
