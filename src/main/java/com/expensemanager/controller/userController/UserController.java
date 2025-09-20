package com.expensemanager.controller.userController;

import com.expensemanager.dto.ApiResponse;
import com.expensemanager.dto.category.ViewCategory;
import com.expensemanager.dto.user.UserResponse;
import com.expensemanager.dto.user.userProfileUpdate.UpdateProfileRequest;
import com.expensemanager.entity.category.Category;
import com.expensemanager.entity.user.User;
import com.expensemanager.service.categoryService.CategoryService;
import com.expensemanager.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    private final CategoryService categoryService;

    public UserController(UserService userService,CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new ApiResponse<>(true, "User details fetched successfully", new UserResponse(
                currentUser.getId(),
                currentUser.getEmail(),
                currentUser.getName(),
                currentUser.getRole(),
                currentUser.getIncomeLimit()
        )));
    }

    @PutMapping("/me")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateProfileRequest upd, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User u = userService.updateProfile(user.getId(), upd);
        UserResponse r = new UserResponse();
        r.setId(u.getId());
        r.setName(u.getName());
        r.setEmail(u.getEmail());
        r.setIncomeLimit(u.getIncomeLimit());
        return ResponseEntity.ok(new ApiResponse<>(true, "User Successfully updated.", r));
    }

    @GetMapping("/me/categories")
    public ResponseEntity<ApiResponse<Set<ViewCategory>>> viewAllUserCategories() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Set<Category> userCategories = categoryService.getCategoriesByUser(currentUser);
        Set<ViewCategory> viewCategoryOfUsers = userCategories.stream().map((category -> new ViewCategory(category.getId(), category.getCategoryName())))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new ApiResponse<>(true, "Categories fetched for given user", viewCategoryOfUsers));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public ResponseEntity<BigDecimal> getIncome(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user.getIncomeLimit());
    }
}

