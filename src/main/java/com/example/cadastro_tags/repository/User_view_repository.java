package com.example.cadastro_tags.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cadastro_tags.model.User_view_model;

public interface User_view_repository extends JpaRepository<User_view_model, Long> {
    
}
