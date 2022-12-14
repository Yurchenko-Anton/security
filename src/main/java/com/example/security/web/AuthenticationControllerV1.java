package com.example.security.web;

import com.example.security.dto.AuthenticationRequestDTO;
import com.example.security.services.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationControllerV1 {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@Qualifier("Authenticate") @RequestBody AuthenticationRequestDTO request) throws AuthenticationException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.logoutUser(request,response);
    }
}