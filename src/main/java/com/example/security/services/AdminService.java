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

    private final UserRepository userRepository;

    private void saveUser(User user) {
        userRepository.save(user);
    }

    private void saveAllUser(List<User> users) {
        userRepository.saveAll(users);
    }

    public List<User> getNotApproveUsers() {
        return userRepository.findAllByStatus(Status.DISAPPROVED);
    }

    public Optional<User> findUsers(Long id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<User> changeRole(Long id, Role role) {
        return findUsers(id).map(userOpt -> {
            userOpt.setRole(role);
            saveUser(userOpt);
            return ResponseEntity.ok(userOpt);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<User> approve(Long id) {
        return findUsers(id).map(userOpt -> {
            userOpt.setStatus(Status.APPROVED);
            saveUser(userOpt);
            return ResponseEntity.ok(userOpt);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public void approveAll() {
        List<User> users = userRepository.findAllByStatus(Status.DISAPPROVED);
        users.forEach(user -> user.setStatus(Status.APPROVED));
        saveAllUser(users);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}