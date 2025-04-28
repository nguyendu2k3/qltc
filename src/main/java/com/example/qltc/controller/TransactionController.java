package com.example.qltc.controller;

import com.example.qltc.dto.request.TransactionRequest;
import com.example.qltc.dto.response.TransactionResponse;
import com.example.qltc.enums.TransactionType;
import com.example.qltc.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    //Lấy tất cả giao dịch của user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionResponse>> getAllByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getAllByUser(userId));
    }
    //Lấy thông tin giao dịch theo ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }
    //Tạo mới giao dịch
    @PostMapping
    public ResponseEntity<TransactionResponse> create(@RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.create(request));
    }
    //Cập nhật thông tin giao dịch
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> update(
            @PathVariable Long id,
            @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.update(id, request));
    }
    //Xóa giao dịch
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Tổng tiền loại giao dịch theo từng tháng trong năm
    @GetMapping("/user/{userId}/total-by-month")
    public ResponseEntity<Map<Integer, Double>> getTotalByMonthInYear(
            @PathVariable Long userId,
            @RequestParam TransactionType type, // Enum nhận trực tiếp từ query param: ?type=INCOME
            @RequestParam int year) {
        return ResponseEntity.ok(transactionService.getTotalByMonthInYear(userId, type, year));
    }

    // Tổng tiền loại giao dịch trong khoảng thời gian
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<Double> getTotalByTypeInPeriod(
            @PathVariable Long userId,
            @RequestParam TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(transactionService.getTotalByTypeInPeriod(userId, type, start, end));
    }
}