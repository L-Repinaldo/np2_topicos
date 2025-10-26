package com.biblioteca.np2.repository;

import com.biblioteca.np2.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE LOWER(u.login) = LOWER(:login)")
    Optional<User> findByLoginIgnoreCase(String login);
}


