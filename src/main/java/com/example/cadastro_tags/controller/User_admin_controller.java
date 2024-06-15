package com.example.cadastro_tags.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cadastro_tags.config.security.TokenService;
import com.example.cadastro_tags.model.User_admin_model;
import com.example.cadastro_tags.model.dto.LoginUser_adm_dto;
import com.example.cadastro_tags.model.dto.ResponseDTO;
import com.example.cadastro_tags.repository.User_admin_repository;
import com.example.cadastro_tags.service.User_admin_service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class User_admin_controller {

    private final User_admin_repository user_admin_repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    private final User_admin_service userAdminService;


    @PostMapping("/login")
    public ResponseEntity<?> fazerLogin(@RequestBody LoginUser_adm_dto body) {
        
        Optional<User_admin_model> optionalUser = this.user_admin_repository.findByEmail(body.email());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build(); // Usuário não encontrado
        }
    
        User_admin_model userAdmin = optionalUser.get();
    
        if (!passwordEncoder.matches(body.password(), userAdmin.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Senha incorreta
        }
    
        String token = this.tokenService.createToken(userAdmin);
        ResponseDTO responseDTO = new ResponseDTO(userAdmin.getId(), userAdmin.getName(), token);
    
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<User_admin_model> registerUserAdmin(@RequestBody User_admin_model userAdmin) {
        User_admin_model savedUserAdmin = userAdminService.saveUserAdmin(userAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserAdmin);
    }
    
    @PutMapping("/editarUserAdmin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User_admin_model> updateUserAdmin(
            @PathVariable Long id,
            @RequestBody User_admin_model updatedUserAdmin) {

        User_admin_model userAdmin = userAdminService.updateUserAdmin(id, updatedUserAdmin);
        return ResponseEntity.ok(userAdmin);
    }

    @GetMapping("/obterUserAdmin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User_admin_model> getUserAdminById(@PathVariable Long id) {
        User_admin_model userAdmin = userAdminService.findUserAdminById(id);
        return ResponseEntity.ok(userAdmin);
    }

    @DeleteMapping("/deletarUserAdmin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserAdmin(@PathVariable Long id) {
        userAdminService.deleteUserAdmin(id);
        return ResponseEntity.noContent().build();
    }


    
}
