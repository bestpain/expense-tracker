package com.expensemanager.dto.expense;

import com.expensemanager.dto.category.ViewCategoryResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ViewExpenseResponse(String id, String description, BigDecimal amount, OffsetDateTime expenseDateTime,
                                  ViewCategoryResponse category) {
}

