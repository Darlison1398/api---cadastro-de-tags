package com.example.cadastro_tags.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

// classe criada apenas para teste. APAGAR ELA DEPOIS.
@RestController
@RequestMapping("/teste")
@RequiredArgsConstructor
public class TesteController {

    @GetMapping
    public ResponseEntity<String> msg() {
        return ResponseEntity.ok("deu certo. Sucesso!");
    }
}
