package com.example.cadastro_tags.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cadastro_tags.model.User_admin_model;
import com.example.cadastro_tags.model.User_view_model;

public interface User_view_repository extends JpaRepository<User_view_model, Long> {
    List<User_admin_model> findByNameIgnoreCaseContaining(String name);

    Optional<User_view_model> findByEmail(String email);
}
