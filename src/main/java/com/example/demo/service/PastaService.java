package com.example.demo.service;

import com.example.demo.config.GeradorProjetoConst;
import com.example.demo.domain.ParametroEnum;
import com.example.demo.domain.Projeto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PastaService {

    public boolean possuiArquivos(File pastaAtualProjetoReferencia) {
        return pastaAtualProjetoReferencia.listFiles() != null;
    }

    public File buscarPasta(String pasta) {
        return new File(GeradorProjetoConst.PATH_RESOURCE.concat(pasta));
    }

    public Path criarPasta(Optional<ParametroEnum> variavelParaRenomear, Projeto projeto,
                           String nomeArquivo, Path pastaEmCriacao) throws IOException {
        if (variavelParaRenomear.isPresent()) {
            String corrigirNomePasta = projeto.substituirValorParametro(nomeArquivo);

            if (corrigirNomePasta.contains(".")) {
                pastaEmCriacao = criarPastasPacote(corrigirNomePasta, pastaEmCriacao);
            }

        } else {
            pastaEmCriacao = criarPasta(nomeArquivo, pastaEmCriacao);
        }

        return pastaEmCriacao;
    }

    private Path criarPastasPacote(String nomePacote, Path pastaAtual) throws IOException {
        String[] pastas = nomePacote.split("\\.");
        for (String nomePasta: pastas) {
            pastaAtual = criarPasta(nomePasta, pastaAtual);
        }
        return pastaAtual;
    }

    private Path criarPasta(String nomePasta, Path pastaAtual) throws IOException {
        Path novaPasta = criarCaminhoLogicoPasta(pastaAtual, nomePasta);
        Files.createDirectory(novaPasta);
        return novaPasta;
    }

    public Path criarCaminhoLogicoPasta(Path caminho, String pastaEmCriacao) {
        return caminho.resolve(pastaEmCriacao);
    }

    public Path voltarPastaAnterior(Path pasta) {
        return pasta.getParent();
    }

    public Path criarPastaRaizProjeto(Path pastaRaizNovoProjeto, String nomeProjeto) throws Exception {
        try {
            Path baseProjeto = Files.createDirectory(pastaRaizNovoProjeto);
            baseProjeto = criarCaminhoLogicoPasta(baseProjeto, nomeProjeto);
            Files.createDirectory(baseProjeto);
            return baseProjeto;
        } catch (IOException e) {
            throw new Exception("Erro ao criar a pasta raiz do projeto: ".concat(nomeProjeto), e);
        }
    }
}
