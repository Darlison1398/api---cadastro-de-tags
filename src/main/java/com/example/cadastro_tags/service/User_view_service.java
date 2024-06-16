package com.example.cadastro_tags.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cadastro_tags.config.security.TokenService;
import com.example.cadastro_tags.model.User_admin_model;
import com.example.cadastro_tags.model.User_view_model;
import com.example.cadastro_tags.model.dto.LoginUser_adm_dto;
import com.example.cadastro_tags.model.dto.ResponseDTO;
import com.example.cadastro_tags.repository.User_admin_repository;
import com.example.cadastro_tags.repository.User_view_repository;

@Service
public class User_view_service {

    @Autowired
    private User_view_repository view_repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;


    public User_view_model createUserView( User_view_model userView ){
        try {
            return view_repository.save(userView);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao tentar criar user_view ", e);
        }
    }

    public ResponseDTO fazerLogin(LoginUser_adm_dto body) {
        Optional<User_view_model> optionalUserView = view_repository.findByEmail(body.email());
        if (optionalUserView.isEmpty()) {
            throw new IllegalStateException("Usuário não encontrado.");
        }
    
        User_view_model userView = optionalUserView.get();
        if (!passwordEncoder.matches(body.password(), userView.getPassword())) {
            throw new IllegalStateException("Senha incorreta.");
        }
    
        String token = this.tokenService.createToken(userView);
        return new ResponseDTO(userView.getId(), userView.getName(), token);
    }
    
    

    public User_view_model getUserView_ById(Long id) {
        return view_repository.findById(id)
               .orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));
    }

    public User_view_model updateUserView(Long id, User_view_model newUserView) {

        User_view_model userView = view_repository.findById(id)
               .orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));

        userView.setName(newUserView.getName());
        userView.setLastname(newUserView.getLastname());
        userView.setAge(newUserView.getAge());
        userView.setEmail(newUserView.getEmail());
        userView.setPassword(newUserView.getPassword());
        userView.setStatus(newUserView.isStatus());
        userView.setDatahoracadastro(newUserView.getDatahoracadastro());

        return view_repository.save(userView);
    }

    public User_view_model deleteUserView(Long id) {
        User_view_model userView = view_repository.findById(id)
               .orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));

        view_repository.deleteById(id);
        return userView;
    }
    
}
