package com.expensemanager.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AddCategoryRequest {
    @NotBlank(message = "Category name cannot be empty")
    @Size(min = 3, max = 30, message = "Category name must between 3 to 30 characters")
    private String categoryName;
}
