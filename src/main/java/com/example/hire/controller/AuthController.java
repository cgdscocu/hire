package com.example.hire.controller;

import com.example.hire.dto.LoginRequest;
import com.example.hire.dto.LoginResponse;
import com.example.hire.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        // Token'dan "Bearer " prefix'ini kaldır
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        authService.logout(token);
        return ResponseEntity.ok("Başarıyla çıkış yapıldı");
    }
}

