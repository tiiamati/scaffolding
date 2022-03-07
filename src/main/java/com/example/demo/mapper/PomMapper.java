package com.example.demo.mapper;

import com.example.demo.domain.ParametroEnum;
import com.example.demo.domain.Projeto;
import com.example.demo.utils.ArquivoUtils;

import java.io.File;

public class PomMapper {

    public static void escrever(File arquivo, Projeto projeto) throws Exception {
        try {
            String conteudo = ArquivoUtils.ler(arquivo)
                    .replace(ParametroEnum.GROUP.value, projeto.grupo)
                    .replace(ParametroEnum.ARTIFACT.value, projeto.artefato)
                    .replace(ParametroEnum.NAME.value, projeto.nome)
                    .replace(ParametroEnum.DESCRIPTION.value, projeto.descricao);

            ArquivoUtils.escrever(arquivo, conteudo);
        } catch (Exception e) {
            throw new Exception("Não foi possível escrever no arquivo", e);
        }
    }
}

