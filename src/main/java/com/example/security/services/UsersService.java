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

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersService {
    private final int ENCRYPT_STRENGTH = 12;

    UserRepository userRepository;
    JwtTokenProvider jwtTokenProvider;

    private void saveUser(User user){
        userRepository.save(user);
    }
    public void changePassword(String token, String password) {
        Long id = Long.parseLong(jwtTokenProvider.decodeToken(token).get("id").toString());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user doesn't exist"));
        user.setPassword(new BCryptPasswordEncoder(ENCRYPT_STRENGTH).encode(password));
        saveUser(user);
    }

    public Status validate(User user) {
        if (user.getRole().equals(Role.ADMIN)) {
            return Status.DISAPPROVED;
        } else {
           return Status.APPROVED;
        }
    }

    public String encryptPass(String pass) {
        return new BCryptPasswordEncoder(ENCRYPT_STRENGTH).encode(pass);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByPhone(String phone){
        return userRepository.findByPhone(phone);
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }
    public User createNewUser(User user){
        user.setPassword(encryptPass(user.getPassword()));
        user.setStatus(validate(user));
        saveUser(user);
        return user;
    }
}
