package com.example.qltc.mapper;

import com.example.qltc.dto.response.TransactionResponse;
import com.example.qltc.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "userId", source = "user.id")
    TransactionResponse toTransactionResponse(Transaction transaction);
    List<TransactionResponse> toTransactionResponseList(List<Transaction> transactions);
}