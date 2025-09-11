package com.expensemanager.entity.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "category",uniqueConstraints = @UniqueConstraint(columnNames = "categoryName"))
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "CHAR_LENGTH(category_name) > 3 AND CHAR_LENGTH(category_name) <= 30")
public class Category {
    @Id
    @UuidGenerator
    @Column(length = 36, updatable = false, nullable = false)
    private String categoryId;

    @NotBlank(message = "Category name cannot be empty")
    @Size(max = 30, message = "Category name must be at most 30 characters")
    @Column(nullable = false, length = 30)   // DB column constraint
    private String categoryName;
}
