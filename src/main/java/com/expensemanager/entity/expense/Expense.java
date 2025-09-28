package com.expensemanager.entity.expense;

import com.expensemanager.entity.BaseEntity;
import com.expensemanager.entity.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Check;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Table(name = "expenses")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Expense extends BaseEntity {
    @Min(value = 1, message = "Amount should be Greater than Zero.")
    @Column(nullable = false, precision = 10, scale = 2)
    @Check(constraints = "amount > 0")
    private BigDecimal amount;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 100, message = "Description must be at most 100 characters")
    @Column(nullable = false)
    @Check(constraints = "LENGTH(description) > 0 AND LENGTH(description) <= 100")
    private String description;

    @NotNull(message = "can not be null")
    private OffsetDateTime expenseDateTime;

    // FK relationship (authoritative for insert/update)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Direct FK column (read-only for lightweight access)
    @Column(name = "category_id", nullable = false, insertable = false, updatable = false)
    private String categoryId;

    public static Expense of(BigDecimal amount, String description, Category category, @Nullable OffsetDateTime expenseDateTime) {
        Expense expense = new Expense();
        expense.amount = amount;
        expense.description = description;
        expense.category = category;
        expense.expenseDateTime = (expenseDateTime != null)
                ? expenseDateTime
                : OffsetDateTime.now(ZoneOffset.ofHoursMinutes(5, 30));
        return expense;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", expenseDateTime=" + expenseDateTime +
                ", category=" + categoryId +
                "} " + super.toString();
    }
}
