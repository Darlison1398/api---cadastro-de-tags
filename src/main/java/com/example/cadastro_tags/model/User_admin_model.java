package com.example.cadastro_tags.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_admim")
@Getter
@Setter
@NoArgsConstructor
public class User_admin_model extends PessoaModel {

    @Column(unique = true, nullable = false )
    private String code_security;
    
}
