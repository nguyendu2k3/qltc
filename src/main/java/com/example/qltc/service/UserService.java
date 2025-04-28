package com.example.qltc.service;

import com.example.qltc.dto.request.UpdateUserRequest;
import com.example.qltc.dto.response.UserResponse;
import com.example.qltc.entity.User;
import com.example.qltc.exception.ResourceNotFoundException;
import com.example.qltc.mapper.UserMapper;
import com.example.qltc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dịch vụ xử lý thông tin người dùng
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    // Lấy thông tin người dùng hiện tại
    public UserResponse getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Username in context: " + username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng!"));
        return userMapper.toUserResponse(user);
    }


    // Lấy thông tin người dùng theo id (chỉ admin)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với id: " + id));
        return userMapper.toUserResponse(user);
    }

    // Lấy toàn bộ người dùng (chỉ admin)
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    // Cập nhật thông tin người dùng hiện tại
    public UserResponse updateCurrentUser(UpdateUserRequest updateUserRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng!"));

        updateUserFields(user, updateUserRequest);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }


    // (Tùy chọn) Cho admin có thể cập nhật thông tin user bất kỳ
    public UserResponse updateUserById(Long id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với id: " + id));

        updateUserFields(user, updateUserRequest);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    private void updateUserFields(User user, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getFullName() != null) {
            user.setFullName(updateUserRequest.getFullName());
        }
        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getPassword() != null) {
            user.setPassword(updateUserRequest.getPassword());
        }
        // thêm nếu muốn sửa gì
    }
}