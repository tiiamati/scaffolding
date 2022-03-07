package com.example.demo.controller;

import com.example.demo.domain.Projeto;
import com.example.demo.mapper.ProjetoMapper;
import com.example.demo.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gerador-projeto")
public class GeradorProjetoController {

    @Autowired
    private ProjetoService service;

    @GetMapping
    public ResponseEntity<Resource> generate(@RequestParam("grupo") String grupo,
                                             @RequestParam("artefato") String artefato,
                                             @RequestParam("nome") String nome,
                                             @RequestParam("descricao") String descricao) throws Exception {
        try {
            Projeto projeto = ProjetoMapper.toModel(grupo, artefato, nome, descricao);

            Resource resource = service.gerarProjeto(projeto);
            String header = service.header(resource);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,  header)
                    .body(resource);

        } catch (Exception e) {
            throw new Exception("Não foi possível gerar o projeto");
        }
    }
}