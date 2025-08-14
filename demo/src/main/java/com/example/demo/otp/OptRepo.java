package com.example.demo.otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OptRepo extends JpaRepository<Otp,Long> {
    Optional<Otp> findByEmail(String email);
    void deleteByEmail(String email);
}
