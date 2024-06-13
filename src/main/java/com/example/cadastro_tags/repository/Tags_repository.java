package com.example.cadastro_tags.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cadastro_tags.model.Tag_model;
import java.util.List;


public interface Tags_repository extends JpaRepository<Tag_model, Long> {

    List<Tag_model> findByNumber(int number);
    
}
