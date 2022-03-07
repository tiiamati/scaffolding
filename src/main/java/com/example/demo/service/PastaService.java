package com.example.demo.service;

import com.example.demo.config.GeradorProjetoConst;
import com.example.demo.domain.Projeto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PastaService {

    public boolean possuiArquivos(File pastaAtualProjetoReferencia) {
        return pastaAtualProjetoReferencia.listFiles() != null;
    }

    public File buscarPasta(String pasta) {
        return new File(GeradorProjetoConst.PATH_RESOURCE.concat(pasta));
    }

    public void criarPasta(Path pasta) throws IOException {
        Files.createDirectory(pasta);
    }

    public Path criarCaminhoLogicoPasta(Path caminho, File pastaEmCriacao) {
        return caminho.resolve(pastaEmCriacao.getName());
    }

    public Path voltarPastaAnterior(Path pasta) {
        return pasta.getParent();
    }

    public Path criarProjetoRaiz(Projeto projeto) throws Exception {
        try {
            Path baseProjeto = Files.createDirectory(Path.of(GeradorProjetoConst.PROJETO_REFERENCIA.concat(projeto.nome)));
            baseProjeto = baseProjeto.resolve(projeto.nome);
            Files.createDirectory(baseProjeto);
            return baseProjeto;
        } catch (IOException e) {
            throw new Exception("Erro ao criar a pasta raiz do projeto: ".concat(projeto.nome), e);
        }
    }
}
