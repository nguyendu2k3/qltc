package com.example.qltc.repository;

import com.example.qltc.entity.Category;
import com.example.qltc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Lấy category global hoặc của user này
    List<Category> findByUserIsNullOrUser(User user);

    // Lấy tất cả global
    List<Category> findByUserIsNull();

    // Lấy tất cả của user này
    List<Category> findByUser(User user);

    Optional<Category> findByNameAndUser(String name, User user);
    Optional<Category> findByNameAndUserIsNull(String name);
    boolean existsByNameAndUser(String name, User user);
    boolean existsByNameAndUserIsNull(String name);
}