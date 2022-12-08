package com.example.security.services;

import com.example.security.entity.User;
import com.example.security.model.Role;
import com.example.security.model.Status;
import com.example.security.repository.UserRepository;
import com.example.security.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersService {
    UserRepository userRepository;
    JwtTokenProvider jwtTokenProvider;

    public User changePassword(String token, String password) {
        User user = userRepository.findById(Long.parseLong(jwtTokenProvider.decodeToken(token).get("id").toString()))
                .orElseThrow(() -> new UsernameNotFoundException("user doesn't exist"));
        user.setPassword(new BCryptPasswordEncoder(12).encode(password));
        return user;
    }

    public User validate(User user) {
        if (user.getRole().equals(Role.ADMIN) || user.getRole().getAuthorities().isEmpty()) {
            user.setStatus(Status.DISAPPROVED);
        } else {
            user.setStatus(Status.APPROVED);
        }
        return user;
    }

    public String codePass(String pass) {
        return new BCryptPasswordEncoder(12).encode(pass);
    }

}
