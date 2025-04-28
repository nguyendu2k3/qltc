package com.example.qltc.dto.response;


import com.example.qltc.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
/**
 * DTO chứa thông tin người dùng trong phản hồi
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String fullName;
    String email;
    Role role;

}
