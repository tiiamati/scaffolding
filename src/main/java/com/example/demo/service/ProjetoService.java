package com.example.demo.service;

import com.example.demo.config.GeradorProjetoConst;
import com.example.demo.domain.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProjetoService {

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private ZipService zipService;

    @Autowired
    private PastaService pastaService;

    public Resource gerarProjeto(Projeto projeto) throws Exception {
        try {
            File projetoReferencia = pastaService.buscarPasta(GeradorProjetoConst.PATH_JAVA);

            if (projetoReferencia != null) {

                // cria o caminho raiz do projeto
                Path baseProjeto = pastaService.criarProjetoRaiz(projeto);

                // percorre o projeto de referência
                arquivoService.criarArquivos(projetoReferencia, baseProjeto);

                String projetoZip = zipService.zip(baseProjeto.toString());

                return new UrlResource(Paths.get(projetoZip).toUri());
            }
            return null;
        } catch (MalformedURLException e) {
            throw new Exception("Problemas ao gerar projeto ", e);
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
