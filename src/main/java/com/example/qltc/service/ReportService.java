package com.example.qltc.service;

import com.example.qltc.entity.User;
import com.example.qltc.enums.TransactionType;
import com.example.qltc.repository.TransactionRepository;
import com.example.qltc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    // Tổng thu hoặc chi theo từng tháng trong năm
    public Map<Integer, Double> getTotalByMonthInYear(Long userId, TransactionType type, int year) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Object[]> results = transactionRepository.getTotalByMonthInYear(user, type, year);
        Map<Integer, Double> monthToTotal = new HashMap<>();
        for (Object[] obj : results) {
            Integer month = (Integer) obj[0];
            Double total = (Double) obj[1];
            monthToTotal.put(month, total);
        }
        return monthToTotal;
    }

    // Tổng thu hoặc chi trong một khoảng thời gian
    public Double getTotalByTypeInPeriod(Long userId, TransactionType type, LocalDate start, LocalDate end) {
        User user = userRepository.findById(userId).orElseThrow();
        Double total = transactionRepository.getTotalByTypeInPeriod(user, type, start, end);
        return total != null ? total : 0.0;
    }

    // Tổng tiền từng category trong khoảng thời gian
    public Map<String, Double> getTotalByCategoryInPeriod(Long userId, TransactionType type, LocalDate start, LocalDate end) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Object[]> results = transactionRepository.getTotalByCategoryInPeriod(user, type, start, end);
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Object[] obj : results) {
            String category = (String) obj[0];
            Double total = (Double) obj[1];
            categoryTotals.put(category, total);
        }
        return categoryTotals;
    }
}