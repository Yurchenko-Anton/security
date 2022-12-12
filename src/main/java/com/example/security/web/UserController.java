package com.example.security.web;

import com.example.security.entity.User;
import com.example.security.security.JwtTokenProvider;
import com.example.security.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;

    @GetMapping
    @PreAuthorize("hasAuthority('users:read')")
    public List<User> getAll() {
        return usersService.getAllUsers();
    }

    @GetMapping("/phone/{phone}")
    @PreAuthorize("hasAuthority('users:read')")
    public Optional<User> getByPhone(@PathVariable String phone) {
        return usersService.getUserByPhone(phone);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public Optional<User> getById(@PathVariable Long id) {
        return usersService.getUserById(id);
    }

    @PostMapping("/registration")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Create new user")
    public User registration(@RequestBody User user) {
        return usersService.createNewUser(user);
    }

    @PutMapping("change/pass")
    @PreAuthorize("hasAuthority('users:read')")
    public void changePass(@RequestHeader("Authorization") String token, @RequestBody String password) {
        usersService.changePassword(token, password);
    }
}