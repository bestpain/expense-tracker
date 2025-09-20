package com.expensemanager.controller.authController;


import com.expensemanager.dto.ApiResponse;
import com.expensemanager.dto.user.userLogin.UserLoginRequest;
import com.expensemanager.dto.user.userLogin.UserLoginResponse;
import com.expensemanager.dto.user.userRegister.UserRegisterRequest;
import com.expensemanager.dto.user.userRegister.UserRegisterResponse;
import com.expensemanager.service.TokenBlacklistService;
import com.expensemanager.service.authService.AuthenticationService;
import com.expensemanager.service.jwtService.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@Slf4j
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    private final TokenBlacklistService blacklistService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, TokenBlacklistService blacklistService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.blacklistService = blacklistService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest registerUserDto) {
        UserRegisterResponse registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User registered successfully", registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> authenticate(@RequestBody @Valid UserLoginRequest loginUserDto) {
        Authentication auth = authenticationService.authenticate(loginUserDto);

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        String jwtToken = jwtService.generateToken(userDetails);

        UserLoginResponse loginResponse = new UserLoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime(jwtToken));

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "No token provided", null));
        }

        String token = authHeader.substring(7);
        blacklistService.blacklistToken(token);

        return ResponseEntity.ok(new ApiResponse<>(true, "Logged out successfully", null));
    }
}
