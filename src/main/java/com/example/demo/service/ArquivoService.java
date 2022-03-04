package com.example.demo.service;

import com.example.demo.domain.Projeto;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Service
public class ArquivoService {

    private static final String PATH_RESOURCE = "src/main/resources/";

    public File buscarArquivo(String arquivo) {
        return new File(PATH_RESOURCE.concat(arquivo));
    }

    public void escrever(File arquivo, Projeto projeto) throws Exception {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));

            StringBuilder conteudo = new StringBuilder();

            conteudo.append("Group: ")
                    .append(projeto.grupo)
                    .append("\n")
                    .append("Artifact: ")
                    .append(projeto.artefato)
                    .append("\n")
                    .append("Name: ")
                    .append(projeto.nome)
                    .append("\n")
                    .append("Description: ")
                    .append(projeto.descricao);

            writer.write(conteudo.toString());
            writer.flush();
            writer.close();

        } catch (Exception e) {
            throw new Exception("Não foi possível escrever no arquivo", e);
        }
    }
}
