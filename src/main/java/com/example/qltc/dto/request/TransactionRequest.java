package com.example.qltc.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {
    Double amount;
    String description;
    LocalDate date;
    String type; // "INCOME" hoặc "EXPENSE"
    Long categoryId;
    Long userId;
}
