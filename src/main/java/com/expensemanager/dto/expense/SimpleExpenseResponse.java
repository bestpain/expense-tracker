package com.expensemanager.dto.expense;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


public record SimpleExpenseResponse(String id, String description, BigDecimal amount, OffsetDateTime expenseDateTime, String categoryId) {}
