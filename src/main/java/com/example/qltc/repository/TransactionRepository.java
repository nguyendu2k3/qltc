package com.example.qltc.repository;

import com.example.qltc.entity.Transaction;
import com.example.qltc.entity.User;
import com.example.qltc.entity.Category;
import com.example.qltc.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

    List<Transaction> findByCategory(Category category);

    List<Transaction> findByUserAndType(User user, TransactionType type);

    List<Transaction> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

    @Query("SELECT FUNCTION('MONTH', t.date), SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.type = :type AND FUNCTION('YEAR', t.date) = :year GROUP BY FUNCTION('MONTH', t.date) ORDER BY FUNCTION('MONTH', t.date)")
    List<Object[]> getTotalByMonthInYear(User user, TransactionType type, int year);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.type = :type AND t.date BETWEEN :start AND :end")
    Double getTotalByTypeInPeriod(User user, TransactionType type, LocalDate start, LocalDate end);

    @Query("SELECT t.category.name, SUM(t.amount) FROM Transaction t WHERE t.user = :user AND t.type = :type AND t.date BETWEEN :start AND :end GROUP BY t.category.name")
    List<Object[]> getTotalByCategoryInPeriod(User user, TransactionType type, LocalDate start, LocalDate end);

    }