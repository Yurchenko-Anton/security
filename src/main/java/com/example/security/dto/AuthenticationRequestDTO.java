package com.example.security.dto;

import lombok.Value;

@Value
public class AuthenticationRequestDTO {
    Long id;
    String password;
}