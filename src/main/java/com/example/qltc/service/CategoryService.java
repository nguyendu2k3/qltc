package com.example.qltc.service;

import com.example.qltc.dto.request.CategoryRequest;
import com.example.qltc.dto.response.CategoryResponse;
import com.example.qltc.entity.Category;
import com.example.qltc.entity.User;
import com.example.qltc.exception.ResourceNotFoundException;
import com.example.qltc.mapper.CategoryMapper;
import com.example.qltc.repository.CategoryRepository;
import com.example.qltc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    // Lấy tất cả category: global hoặc của user này
    public List<CategoryResponse> getAll(Long userId) {
        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }
        List<Category> categories = (user != null)
                ? categoryRepository.findByUserIsNullOrUser(user)
                : categoryRepository.findByUserIsNull();
        return categoryMapper.toCategoryResponseList(categories);
    }

    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return categoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse create(CategoryRequest request) {
        User user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .user(user)
                .build();
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        // Không cho đổi user khi update
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}