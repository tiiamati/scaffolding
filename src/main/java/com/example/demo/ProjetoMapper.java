package com.example.demo;

import lombok.Builder;

@Builder
public class ProjetoMapper {

    public static Projeto toModel(String grupo, String artefato, String nome, String descricao) {
        return Projeto.builder()
                .grupo(grupo)
                .artefato(artefato)
                .nome(nome)
                .descricao(descricao)
                .build();
    }
}
