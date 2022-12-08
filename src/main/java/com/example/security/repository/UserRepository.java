package com.example.security.repository;

import com.example.security.entity.User;
import com.example.security.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstName(String firstName);

    Optional<User> findByPhone(String phone);

    List<User> findAllByStatus(Status status);

    User findTopByOrderByIdDesc();

}
