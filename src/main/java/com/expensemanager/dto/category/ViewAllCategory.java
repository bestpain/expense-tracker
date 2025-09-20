package com.expensemanager.dto.category;

import com.expensemanager.dto.user.SimpleUserResponse;
import com.expensemanager.entity.user.User;

public record ViewAllCategory(String id, String name, SimpleUserResponse user) {
}
