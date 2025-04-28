package com.example.qltc.controller;

import com.example.qltc.dto.request.CategoryRequest;
import com.example.qltc.dto.response.CategoryResponse;
import com.example.qltc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // Lấy tất cả: nếu có userId thì lấy cả global + cá nhân, không thì chỉ global
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(categoryService.getAll(userId));
    }
    // Lấy thông tin category theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }
    // Tạo mới category
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.create(request));
    }
    // Cập nhật thông tin category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable Long id,
            @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }
    // Xóa category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}