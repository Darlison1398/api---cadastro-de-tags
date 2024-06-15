package com.example.cadastro_tags.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_admim")
@Getter
@Setter
@NoArgsConstructor
public class User_admin_model extends PessoaModel  implements UserDetails{

    @Column(unique = true, nullable = false )
    private String code_security;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_ADMIN");
    }

    @Override
    public String getUsername() {
        return getEmail();
    }
    
}
