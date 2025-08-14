package com.example.demo.UserLogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PendingUserRepo extends JpaRepository<pendingUser,Long> {
    Optional<pendingUser> findByEmail(String email);
}
