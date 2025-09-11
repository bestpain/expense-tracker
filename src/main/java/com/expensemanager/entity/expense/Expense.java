package com.expensemanager.entity.expense;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Table(name = "expenses")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @UuidGenerator
    @Column(length = 36, updatable = false, nullable = false)
    private String id;

    @Min(value = 1 , message = "Amount should be Greater than Zero.")
    @Column(nullable = false)
    @Check(constraints = "amount > 0")
    private double amount;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 100, message = "Description must be at most 100 characters")
    @Column(nullable = false)
    @Check(constraints = "LENGTH(description) > 0 AND LENGTH(description) <= 100")
    private String description;

    private LocalDate expenseDate;

}
