package com.example.security.feign;

import com.example.security.dto.AuthenticationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "feignLogin", url = "http://localhost:8080/api/v1/auth")
public interface TestFeignLoginClient {
    @PostMapping("/login")
    ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthenticationRequestDTO request);
}
