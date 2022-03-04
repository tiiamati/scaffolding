package com.example.demo.domain;

import lombok.Builder;

@Builder
public class Projeto {
    public String grupo;
    public String artefato;
    public String nome;
    public String descricao;
    public String nomePacote;
    public String nomeProjetoClasse;
}
