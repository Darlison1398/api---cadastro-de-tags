package com.example.cadastro_tags.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cadastro_tags.model.Tag_model;
import com.example.cadastro_tags.model.User_admin_model;
import com.example.cadastro_tags.model.User_view_model;
import com.example.cadastro_tags.repository.Tags_repository;
import com.example.cadastro_tags.repository.User_admin_repository;
import com.example.cadastro_tags.repository.User_view_repository;

@Service
public class Tag_service {
    
    @Autowired
    private Tags_repository tagRepository;

    @Autowired
    private User_view_repository userViewRepository;

    @Autowired
    private User_admin_repository userAdminRepository;


    public List<Tag_model> allTag() {
        try {
            return tagRepository.findAll();
            
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao tentar listar tag ", e);
        }
    }

    public Tag_model getTagById(Long id) {
        return tagRepository.findById(id)
               .orElseThrow(() -> new IllegalStateException("Tag não encontrada."));
    }

    public Tag_model save_tag(String adminEmail, Tag_model tag) {
        try {
            // Verifica se a tag já existe

            ////
            /// ver aqui depois

            if (tagRepository.existsByNumber(tag.getNumber())) {
                throw new IllegalStateException("Tag já existe.");
            }

            Long userViewId = tag.getUser_view().getId();
            User_view_model userViewModel = userViewRepository.findById(userViewId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    
            tag.setUser_view(userViewModel);
            return tagRepository.save(tag);

        } catch (Exception e) {
            throw new RuntimeException("Não foi possível salvar a tag", e);
        }
    }
    

    public Tag_model updateTag(String email, Long id, Tag_model newTag) {
        try {
            User_admin_model userAdmin = userAdminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Tag_model tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Tag não encontrada."));
    
            /*if (!tag.getUser_view().getId().equals(userAdmin.getId())) {
                throw new IllegalStateException("Você não tem permissão para alterar essa tag.");
            }*/
    
            tag.setNumber(newTag.getNumber());
            tag.setStatus(newTag.isStatus());
            tag.setDatahoracadastro(newTag.getDatahoracadastro());
    
            return tagRepository.save(tag);
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao tentar atualizar tag ", e);
        }
    }
    

    public void deleteTag(String email, Long id) {
        try {
            
            User_admin_model userAdmin = userAdminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Tag_model tag = tagRepository.findById(id)
               .orElseThrow(() -> new IllegalStateException("Tag não encontrada."));
            
            /*if (!tag.getUser_view().equals(userAdmin)) {
                throw new RuntimeException("Você não tem permissão para excluir essa tag");
            }*/

            tagRepository.delete(tag);

        } catch (Exception e) {
            throw new IllegalStateException("Erro ao tentar deletar tag ", e);
        }
    }


}
