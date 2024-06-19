package com.example.cadastro_tags.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cadastro_tags.model.Tag_model;
import com.example.cadastro_tags.model.User_admin_model;
import com.example.cadastro_tags.service.Tag_service;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class Tag_controller {

    private final Tag_service tag_service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Tag_model> getTags() {
        try {
            List<Tag_model> tags = tag_service.allTag();
            return tags;
        } catch (Exception e) {
            throw new IllegalStateException("Tags não encontradas ", e);
        }
    }

    @PostMapping("/saveTag")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tag_model> createtag(@AuthenticationPrincipal User_admin_model userAdm, @RequestBody Tag_model tag) {
        try {
            return ResponseEntity.ok(tag_service.save_tag(userAdm.getEmail(), tag));
        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível salvar tag", e);
        }

    }

    @PutMapping("/updateTag/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tag_model> editTag(@AuthenticationPrincipal User_admin_model user, @PathVariable Long id, @RequestBody Tag_model tag) {
        try {
            return ResponseEntity.ok(tag_service.updateTag(user.getEmail(), id, tag));
        } catch (Exception e) {
            
            throw new IllegalStateException("Não foi possível editar tag", e);
        }
    }
    

    @DeleteMapping("/deletarTag/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void apagarTag(Authentication authentication, @PathVariable Long id) {
        try {
            String email = authentication.getName();
            tag_service.deleteTag(email, id);

        } catch (Exception e) {
            throw new IllegalStateException("Não foi possível apagar tag", e);
        }
    }
    
}
