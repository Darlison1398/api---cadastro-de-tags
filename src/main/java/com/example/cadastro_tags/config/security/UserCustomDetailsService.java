package com.example.cadastro_tags.config.security;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.cadastro_tags.model.User_admin_model;
import com.example.cadastro_tags.model.User_view_model;
import com.example.cadastro_tags.repository.User_admin_repository;
import com.example.cadastro_tags.repository.User_view_repository;

@Component
public class UserCustomDetailsService implements UserDetailsService{

    @Autowired
    private User_admin_repository user_admin_repository;

    @Autowired
    private User_view_repository user_view_repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User_admin_model> adminUser = user_admin_repository.findByEmail(username);
        if (adminUser.isPresent()) {
            return adminUser.get();
        }

        Optional<User_view_model> viewUser = user_view_repository.findByEmail(username);
        if (viewUser.isPresent()) {
            return viewUser.get();
        }

        throw new UsernameNotFoundException("User not found with email: " + username);


    }
    
}
