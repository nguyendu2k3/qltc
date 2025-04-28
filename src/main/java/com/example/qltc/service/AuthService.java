package com.example.qltc.service;

import com.example.qltc.dto.request.LoginRequest;
import com.example.qltc.dto.request.RegisterRequest;
import com.example.qltc.dto.response.JwtResponse;
import com.example.qltc.entity.User;
import com.example.qltc.enums.Role;
import com.example.qltc.repository.UserRepository;
import com.example.qltc.security.JwtTokenProvider;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Dịch vụ xử lý xác thực và đăng ký người dùng
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;


    //Login và tạo token
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        String jwt = jwtTokenProvider.generateToken(authentication);

        return new JwtResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                Collections.singletonList(user.getRole().name())
        );
    }

    //Register và bảo mật Bycrypt
    public void registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

}