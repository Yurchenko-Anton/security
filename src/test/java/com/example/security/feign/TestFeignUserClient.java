package com.example.security.feign;

import com.example.security.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "feignUser", url = "http://localhost:8080/api/v1")
public interface TestFeignUserClient {

    @GetMapping
    List<User> getUsers(@RequestHeader HttpHeaders headers);

    @GetMapping("/{id}")
    User getUser(@RequestHeader HttpHeaders headers, @PathVariable Long id);

    @PostMapping("/registration")
    User registration(@RequestBody User user);

    @GetMapping("/phone/{phone}")
    User getUserByPhone(@RequestHeader HttpHeaders headers, @PathVariable String phone);
}
