package com.vijay.controller;


import com.vijay.exception.UserException;
import com.vijay.payload.dto.UserDto;
import com.vijay.payload.request.ForgotPasswordRequest;
import com.vijay.payload.request.LoginRequest;
import com.vijay.payload.request.ResetPasswordRequest;
import com.vijay.payload.response.ApiResponse;
import com.vijay.payload.response.AuthResponse;
import com.vijay.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUpHandler(@RequestBody @Valid UserDto req) throws UserException {

        AuthResponse res = authService.signUp(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody @Valid LoginRequest req) throws UserException {

        AuthResponse res = authService.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) throws UserException {

        authService.createPasswordResetToken(request.getEmail());

        ApiResponse response = new ApiResponse("A reset link was sent to your email" , true);
        return ResponseEntity.ok(response);


    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest request) throws Exception {

        authService.resetPassword(request.getToken(), request.getPassword());
        ApiResponse response = new ApiResponse("Password reset successfully", true);
        return ResponseEntity.ok(response);


    }
}
