package com.example.news_portal.repository;

import com.example.news_portal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DatabaseUserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT user_name FROM users WHERE user_name = :name", nativeQuery = true)
    Optional<String> findUserByName(String name);

    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByUsername(String username);

}
