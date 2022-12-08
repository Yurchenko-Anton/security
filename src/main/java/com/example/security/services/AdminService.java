package com.example.security.services;

import com.example.security.entity.User;
import com.example.security.model.Role;
import com.example.security.model.Status;
import com.example.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    UserRepository userRepository;

    public Optional<User> findUsers(Long id) {
        return userRepository.findById(id);

    }

    public ResponseEntity<User> changeRole(Long id, Role role) {
        Optional<User> user = findUsers(id);
        if (user.isPresent()) {
            user.get().setRole(role);
            userRepository.save(user.get());
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<User> approve(Long id) {
        Optional<User> user = findUsers(id);
        if (user.isPresent()) {
            user.get().setStatus(Status.APPROVED);
            userRepository.save(user.get());
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<User> approveAll() {
        List<User> users = userRepository.findAllByStatus(Status.DISAPPROVED);
        users.forEach(user -> user.setStatus(Status.APPROVED));
        return users;
    }
}
