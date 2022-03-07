package com.example.demo.service;

import com.example.demo.domain.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.commons.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProjetoService {

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private ZipService zipService;

    private static final String PATH_JAVA = "JAVA";

    public Resource projeto(Projeto projeto) throws Exception {
        try {
            File file = arquivoService.buscarArquivo(PATH_JAVA);

            if (file != null) {
                Path baseProjeto = criarProjetoRaiz(projeto);
                percorrerArquivos(file, baseProjeto);

                String projetoZip = zipService.zip(baseProjeto.toString());

                return new UrlResource(Paths.get(projetoZip).toUri());
            }
            return null;
        } catch (MalformedURLException e) {
            throw new Exception("Problemas ao gerar projeto ", e);
        }
    }

    private void percorrerArquivos(File pastaAtual, Path arquivoEmCriacao) throws IOException {
        for (File arquivoAtual: pastaAtual.listFiles()) {
            System.out.println(arquivoAtual.getName());

            if (arquivoAtual.listFiles() != null) {
                // cria uma nova pasta dentro do projeto que estamos gerando
                arquivoEmCriacao = arquivoEmCriacao.resolve(arquivoAtual.getName());
                Files.createDirectory(arquivoEmCriacao);

                percorrerArquivos(arquivoAtual, arquivoEmCriacao);

                // volta uma pasta
                arquivoEmCriacao = arquivoEmCriacao.getParent();
            } else {
                FileUtils.copyFileToDirectory(arquivoAtual, arquivoEmCriacao.toFile());
            }
        }
    }

    private static final String PATH_BASE_PROJETO_CLONE = "src/main/resources/JAVA-";

    private Path criarProjetoRaiz(Projeto projeto) throws Exception {
        try {
            Path baseProjeto = Files.createDirectory(Path.of(PATH_BASE_PROJETO_CLONE.concat(projeto.nome)));
            baseProjeto = baseProjeto.resolve(projeto.nome);
            Files.createDirectory(baseProjeto);
            return baseProjeto;
        } catch (IOException e) {
            throw new Exception("Erro ao criar a pasta raiz do projeto: ".concat(projeto.nome), e);
        }
    }

    public String header(Resource resource) {
        StringBuilder header = new StringBuilder();

        header.append("attachment; filename=\"")
                .append(resource.getFilename())
                .append("\"");

        return header.toString();
    }
}
