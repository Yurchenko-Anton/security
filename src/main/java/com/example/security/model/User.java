package com.example.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class User {
    private Long id;
    private String firstName;
    private String lastName;
}
