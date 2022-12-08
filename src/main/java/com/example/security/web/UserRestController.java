package com.example.security.web;

import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import com.example.security.security.JwtTokenProvider;
import com.example.security.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class UserRestController {

    UserRepository userRepository;
    JwtTokenProvider jwtTokenProvider;
    UsersService usersService;

    @GetMapping
    @PreAuthorize("hasAuthority('users:read')")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/phone/{phone}")
    @PreAuthorize("hasAuthority('users:read')")
    public Optional<User> getByPhone(@PathVariable String phone) {
        return userRepository.findByPhone(phone);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public Optional<User> getById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @PostMapping("/registration")
    @PreAuthorize("permitAll()")
    public User registration(@RequestBody User user) {
        user = usersService.validate(user);
        user.setPassword(usersService.codePass(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @PutMapping("change/pass")
    @PreAuthorize("hasAuthority('users:read')")
    public void changePass(@RequestHeader("Authorization") String token, @RequestBody String password) {
        userRepository.save(usersService.changePassword(token, password));
    }
}
