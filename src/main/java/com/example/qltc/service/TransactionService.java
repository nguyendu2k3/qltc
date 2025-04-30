package com.example.qltc.service;

import com.example.qltc.dto.request.TransactionRequest;
import com.example.qltc.dto.response.TransactionResponse;
import com.example.qltc.entity.Category;
import com.example.qltc.entity.Transaction;
import com.example.qltc.enums.TransactionType;
import com.example.qltc.entity.User;
import com.example.qltc.exception.ResourceNotFoundException;
import com.example.qltc.mapper.TransactionMapper;
import com.example.qltc.repository.CategoryRepository;
import com.example.qltc.repository.TransactionRepository;
import com.example.qltc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;

    public List<TransactionResponse> getAllByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Transaction> transactions = transactionRepository.findByUser(user);
        return transactionMapper.toTransactionResponseList(transactions);
    }

    public TransactionResponse getById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return transactionMapper.toTransactionResponse(transaction);
    }

    public TransactionResponse create(TransactionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        TransactionType type = parseTransactionType(request.getType());

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .type(type)
                .category(category)
                .user(user)
                .build();
        transactionRepository.save(transaction);
        return transactionMapper.toTransactionResponse(transaction);
    }

    public TransactionResponse update(Long id, TransactionRequest request) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        TransactionType type = parseTransactionType(request.getType());

        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setDate(request.getDate());
        transaction.setType(type);
        transaction.setCategory(category);
        transactionRepository.save(transaction);
        return transactionMapper.toTransactionResponse(transaction);
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    // Thống kê theo tháng trong năm
    public Map<Integer, Double> getTotalByMonthInYear(Long userId, TransactionType type, int year) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Object[]> results = transactionRepository.getTotalByMonthInYear(user, type, year);
        Map<Integer, Double> monthToTotal = new HashMap<>();
        for (Object[] obj : results) {
            Integer month = (Integer) obj[0];
            Double total = (Double) obj[1];
            monthToTotal.put(month, total);
        }
        return monthToTotal;
    }

    public Double getTotalByTypeInPeriod(Long userId, TransactionType type, LocalDate start, LocalDate end) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Double total = transactionRepository.getTotalByTypeInPeriod(user, type, start, end);
        return total != null ? total : 0.0;
    }

    // Helper để parse type (String -> Enum) an toàn
    private TransactionType parseTransactionType(String typeStr) {
        try {
            return TransactionType.valueOf(typeStr.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid transaction type: " + typeStr);
        }
    }
    public List<TransactionResponse> getAllByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        List<Transaction> transactions = transactionRepository.findByCategory(category);
        return transactionMapper.toTransactionResponseList(transactions);
    }

    public List<TransactionResponse> getAllByUserAndType(Long userId, TransactionType type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Transaction> transactions = transactionRepository.findByUserAndType(user, type);
        return transactionMapper.toTransactionResponseList(transactions);
    }
}