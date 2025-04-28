package com.example.qltc.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {
    BigDecimal totalIncome;
    BigDecimal totalExpense;
    BigDecimal balance;

}