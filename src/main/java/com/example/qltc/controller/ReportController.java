package com.example.qltc.controller;

import com.example.qltc.enums.TransactionType;
import com.example.qltc.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    // Tổng thu hoặc chi theo từng tháng trong năm
    @GetMapping("/user/{userId}/total-by-month")
    public ResponseEntity<Map<Integer, Double>> getTotalByMonthInYear(
            @PathVariable Long userId,
            @RequestParam TransactionType type,
            @RequestParam int year) {
        return ResponseEntity.ok(reportService.getTotalByMonthInYear(userId, type, year));
    }

    // Tổng thu hoặc chi trong một khoảng thời gian
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<Double> getTotalByTypeInPeriod(
            @PathVariable Long userId,
            @RequestParam TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(reportService.getTotalByTypeInPeriod(userId, type, start, end));
    }

    // Tổng tiền từng category trong khoảng thời gian
    @GetMapping("/user/{userId}/total-by-category")
    public ResponseEntity<Map<String, Double>> getTotalByCategoryInPeriod(
            @PathVariable Long userId,
            @RequestParam TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(reportService.getTotalByCategoryInPeriod(userId, type, start, end));
    }
}