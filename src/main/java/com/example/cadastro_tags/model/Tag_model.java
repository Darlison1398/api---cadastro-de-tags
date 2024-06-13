package com.example.cadastro_tags.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tags")
@Getter
@Setter
@NoArgsConstructor
public class Tag_model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private int number;

    @Column
    private boolean status;

    // vai ser o tipo de ação: se abriu ou fechou a porta = Open or Close
    /*@Column
    private String last_action;*/

    @Column
    private Date datahoracadastro;

    @OneToOne
    @JoinColumn(name = "user_view_id")
    private User_view_model user_view;
    
}
