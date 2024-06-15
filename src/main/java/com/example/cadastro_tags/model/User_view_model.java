package com.example.cadastro_tags.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_view")
@Getter
@Setter
@NoArgsConstructor
public class User_view_model extends PessoaModel implements UserDetails {
    
    @Column
    private boolean status;

    @Column
    private Date datahoracadastro;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_VIEW"));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

}
