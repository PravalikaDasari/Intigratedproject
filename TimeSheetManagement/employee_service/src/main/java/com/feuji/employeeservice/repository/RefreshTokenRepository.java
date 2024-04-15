package com.feuji.employeeservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.feuji.employeeservice.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
}
