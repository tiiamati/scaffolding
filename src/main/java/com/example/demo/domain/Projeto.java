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

    public String substituirValorParametro(String textoComParametro) {

        if (textoComParametro == null || textoComParametro.trim().equals("")) return textoComParametro;

        if (textoComParametro.contains(ParametroEnum.PROJECT_CLASS_NAME.value)) {
            textoComParametro = textoComParametro.replace(ParametroEnum.PROJECT_CLASS_NAME.value, this.nomeProjetoClasse);
        }

        if (textoComParametro.contains(ParametroEnum.PACKAGE.value)) {
            textoComParametro = textoComParametro.replace(ParametroEnum.PACKAGE.value, this.nomePacote);
        }

        if (textoComParametro.contains(ParametroEnum.GROUP.value)) {
            textoComParametro = textoComParametro.replace(ParametroEnum.GROUP.value, this.grupo);
        }

        if (textoComParametro.contains(ParametroEnum.ARTIFACT.value)) {
            textoComParametro = textoComParametro.replace(ParametroEnum.ARTIFACT.value, this.artefato);
        }

        if (textoComParametro.contains(ParametroEnum.DESCRIPTION.value)) {
            textoComParametro = textoComParametro.replace(ParametroEnum.DESCRIPTION.value, this.descricao);
        }

        return textoComParametro;
    }
}
