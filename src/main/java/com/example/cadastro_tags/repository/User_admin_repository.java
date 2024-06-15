package com.example.cadastro_tags.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cadastro_tags.model.User_admin_model;

public interface User_admin_repository extends JpaRepository<User_admin_model, Long> {
    Optional<User_admin_model> findByEmail(String email);
}
