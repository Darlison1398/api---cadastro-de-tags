package com.example.cadastro_tags.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cadastro_tags.model.User_view_model;
import com.example.cadastro_tags.model.dto.LoginUser_adm_dto;
import com.example.cadastro_tags.model.dto.ResponseDTO;
import com.example.cadastro_tags.service.User_view_service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/userView")
@RequiredArgsConstructor
public class User_view_controller {

    private final User_view_service userViewService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User_view_model> salvarUserView(@RequestBody User_view_model user) {
        try {
            User_view_model createdUser = userViewService.createUserView(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/loginUserView")
    public ResponseEntity<?> loginUserView(@RequestBody LoginUser_adm_dto body) {
        try {
            ResponseDTO response = userViewService.fazerLogin(body);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    
}
