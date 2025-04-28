package com.example.qltc.mapper;


import com.example.qltc.dto.response.UserResponse;
import com.example.qltc.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}