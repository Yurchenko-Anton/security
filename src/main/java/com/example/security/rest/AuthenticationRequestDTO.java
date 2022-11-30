package com.example.security.rest;

import lombok.Getter;
import lombok.Value;

@Value
public class AuthenticationRequestDTO {

    private String firstName;
    private String password;


}
