package com.example.security.repository;

import com.example.security.entity.JWT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JWTRepository extends JpaRepository<JWT,Long> {
}
