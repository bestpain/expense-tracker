package com.expensemanager.dto.user.userCategory;

import com.expensemanager.entity.category.Category;
import lombok.Getter;

import java.util.Set;

@Getter
public class UserCategoriesResponse {
    private String id;
    private String name;
    private String email;
    private Set<Category> categories;

    public UserCategoriesResponse(String id, String name, String email, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.categories = categories;
    }
}
