package com.expensemanager.controller.userController;

import com.expensemanager.dto.user.userRegister.UserRegisterRequest;
import com.expensemanager.dto.user.userRegister.UserRegisterResponse;
import com.expensemanager.entity.user.User;
import com.expensemanager.service.userService.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody @Valid UserRegisterRequest user) {
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(new UserRegisterResponse(registeredUser.getId(), registeredUser.getName(),
                registeredUser.getEmail(), registeredUser.getCreatedAt()), HttpStatus.CREATED);
    }
}
