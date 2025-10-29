package com.example.hire.service;

import com.example.hire.dto.LoginRequest;
import com.example.hire.dto.LoginResponse;
import com.example.hire.entity.User;
import com.example.hire.exception.BusinessException;
import com.example.hire.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder, 
                      JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        // Kullanıcıyı bul
        Optional<User> userOptional = userRepository.findByUsernameAndIsActiveTrue(loginRequest.getUsername());
        
        if (userOptional.isEmpty()) {
            throw new BusinessException("Kullanıcı bulunamadı veya aktif değil");
        }

        User user = userOptional.get();

        // Şifre kontrolü
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BusinessException("Geçersiz şifre");
        }

        // Token oluştur
        String token = jwtService.generateToken(user.getUsername());

        // Response oluştur
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());

        return response;
    }

    public void logout(String token) {
        // JWT stateless olduğu için logout işlemi client-side'da token'ı silmek
        // Server-side'da özel bir işlem yapmaya gerek yok
        // İsteğe bağlı olarak blacklist mekanizması eklenebilir
    }
}

