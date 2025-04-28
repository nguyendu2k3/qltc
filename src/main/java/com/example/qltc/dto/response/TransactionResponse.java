package com.example.qltc.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    Long id;
    Double amount;
    String description;
    LocalDate date;
    String type;
    Long categoryId;
    String categoryName;
    Long userId;
}