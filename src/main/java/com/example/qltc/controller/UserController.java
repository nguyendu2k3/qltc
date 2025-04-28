package com.example.qltc.controller;

import com.example.qltc.dto.request.UpdateUserRequest;
import com.example.qltc.dto.response.ApiResponse;
import com.example.qltc.dto.response.UserResponse;
import com.example.qltc.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý thông tin người dùng
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    // Lấy thông tin người dùng hiện tại
    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserResponse userResponse = userService.getCurrentUser();
        return ResponseEntity.ok(userResponse);
    }

    // Lấy thông tin người dùng theo ID
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    //Lấy toàn bộ người dùng
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> userResponse = userService.getAllUsers();
        return ResponseEntity.ok(userResponse);
    }

    //Sửa thông tin người dùng
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserResponse>> updateCurrentUser(
            @Valid @RequestBody UpdateUserRequest updateUserRequest
    ) {
        UserResponse updatedUser = userService.updateCurrentUser(updateUserRequest);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(true, "Cập nhật thông tin người dùng thành công!", updatedUser);
        return ResponseEntity.ok(apiResponse);
    }

    // admin sửa thông tin người dùng theo ID
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserById(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest updateUserRequest
    ) {
        UserResponse updatedUser = userService.updateUserById(id, updateUserRequest);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(true, "Cập nhật thông tin người dùng thành công!", updatedUser);
        return ResponseEntity.ok(apiResponse);
    }
}