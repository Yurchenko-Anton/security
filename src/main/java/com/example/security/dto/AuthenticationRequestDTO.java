package com.example.security.dto;

import lombok.Value;

@Value
public class AuthenticationRequestDTO {
    String phone;
    String password;
}
