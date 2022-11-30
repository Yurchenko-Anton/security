package com.example.security.entity;

import com.example.security.model.Role;
import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
