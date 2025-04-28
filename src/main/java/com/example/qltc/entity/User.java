package com.example.qltc.entity;

import com.example.qltc.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 50)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, length = 100)
    String fullName;

    @Column(nullable = false, unique = true, length = 100)
    String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role;


}