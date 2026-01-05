package com.fintech.repository;

import com.fintech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // THIS WAS MISSING 👇
    boolean existsByUsername(String username);
}
