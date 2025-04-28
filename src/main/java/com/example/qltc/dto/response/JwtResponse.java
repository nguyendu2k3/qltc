package com.example.qltc.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;

/**
 * DTO chứa thông tin phản hồi sau khi xác thực JWT
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse {
    String token;
    String type = "Bearer";
    Long id;
    String username;
    String email;
    String fullName;
    List<String> roles;

    public JwtResponse(String token, Long id, String username, String email, String fullName, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
    }
}