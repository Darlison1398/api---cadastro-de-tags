package com.example.cadastro_tags.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_view")
@Getter
@Setter
@NoArgsConstructor
public class User_view_model extends PessoaModel {
    
    @Column
    private boolean status;

    @Column
    private Date datahoracadastro;

}
