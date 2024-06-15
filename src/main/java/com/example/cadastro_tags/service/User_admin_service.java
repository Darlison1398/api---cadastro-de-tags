package com.example.cadastro_tags.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cadastro_tags.model.User_admin_model;
import com.example.cadastro_tags.repository.User_admin_repository;

@Service
public class User_admin_service {

    @Autowired
    private User_admin_repository user_admin_repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User_admin_model saveUserAdmin(User_admin_model userAdmin) {
        // Verifica se o e-mail já está cadastrado
        /*if (user_admin_repository.existsByEmail(userAdmin.getEmail())) {
            throw new IllegalStateException("E-mail já cadastrado.");
        }*/
        // Codifica a senha antes de salvar
        userAdmin.setPassword(passwordEncoder.encode(userAdmin.getPassword()));
        return user_admin_repository.save(userAdmin);
    }

    public User_admin_model updateUserAdmin(Long id, User_admin_model updatedUserAdmin) {
        // Verifica se o usuário existe
        User_admin_model userAdmin = user_admin_repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));

        // Atualiza os dados do usuário
        userAdmin.setName(updatedUserAdmin.getName());
        userAdmin.setEmail(updatedUserAdmin.getEmail());
        userAdmin.setPassword(passwordEncoder.encode(updatedUserAdmin.getPassword()));
        userAdmin.setCode_security(updatedUserAdmin.getCode_security());

        return user_admin_repository.save(userAdmin);
    }

    public User_admin_model findUserAdminById(Long id) {
        return user_admin_repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));
    }

    public void deleteUserAdmin(Long id) {
        user_admin_repository.deleteById(id);
    }
}
