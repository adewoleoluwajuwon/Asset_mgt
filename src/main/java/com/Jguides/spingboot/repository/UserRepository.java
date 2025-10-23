package com.Jguides.spingboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Jguides.spingboot.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
