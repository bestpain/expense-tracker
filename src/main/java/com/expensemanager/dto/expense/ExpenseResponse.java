package com.expensemanager.dto.expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record ExpenseResponse(
        String id,
        String description,
        BigDecimal amount,
        OffsetDateTime expenseDateTime
) {}