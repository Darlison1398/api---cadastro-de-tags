package com.example.cadastro_tags.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cadastro_tags.model.User_admin_model;

public interface User_admin_repository extends JpaRepository<User_admin_model, Long> {
    List<User_admin_model> findByNameIgnoreCaseContaining(String name);
}
