package com.example.qltc.entity;

import com.example.qltc.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Double amount;

    @Column(length = 255)
    String description;

    @Column(nullable = false)
    LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    TransactionType type;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}