package com.expensemanager.controller.userController;

import com.expensemanager.dto.ApiResponse;
import com.expensemanager.dto.user.UserResponse;
import com.expensemanager.entity.user.User;
import com.expensemanager.service.userService.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        log.info("current user {}", currentUser);
        return ResponseEntity.ok(new ApiResponse<>(true, "User details fetched successfully", new UserResponse(
                currentUser.getId(),
                currentUser.getEmail(),
                currentUser.getRole()
        )));
    }

//    @GetMapping("/")
//    public ResponseEntity<List<User>> allUsers() {
//        List <User> users = userService.allUsers();
//
//        return ResponseEntity.ok(users);
//    }
}

