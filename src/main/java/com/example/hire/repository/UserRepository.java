package com.example.hire.repository;

import com.example.hire.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Username ile kullanıcı bulma
    Optional<User> findByUsername(String username);

    // Email ile kullanıcı bulma
    Optional<User> findByEmail(String email);

    // Username'in var olup olmadığını kontrol etme
    boolean existsByUsername(String username);

    // Email'in var olup olmadığını kontrol etme
    boolean existsByEmail(String email);

    // Aktif kullanıcıları bulma
    Optional<User> findByUsernameAndIsActiveTrue(String username);

    // Aktif kullanıcıları email ile bulma
    Optional<User> findByEmailAndIsActiveTrue(String email);
}

