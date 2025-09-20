package com.expensemanager.dto.category;

import com.expensemanager.dto.user.SimpleUserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCategoryResponse {
    private String categoryId;
    private String categoryName;
    private SimpleUserResponse user;
}
