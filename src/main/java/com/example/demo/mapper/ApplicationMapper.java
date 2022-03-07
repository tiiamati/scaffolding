package com.example.demo.mapper;

import com.example.demo.domain.ParametroEnum;
import com.example.demo.domain.Projeto;
import com.example.demo.utils.ArquivoUtils;

import java.io.File;

public class ApplicationMapper {

    public static void escrever(File arquivo, Projeto projeto) throws Exception {
        try {
            String conteudo = ArquivoUtils.ler(arquivo)
                    .replace(ParametroEnum.PROJECT_CLASS_NAME.value, projeto.nomeProjetoClasse)
                    .replace(ParametroEnum.PACKAGE.value, projeto.nomePacote);

            ArquivoUtils.escrever(arquivo, conteudo);
        } catch (Exception e) {
            throw new Exception("Não foi possível escrever no arquivo", e);
        }
    }
}
