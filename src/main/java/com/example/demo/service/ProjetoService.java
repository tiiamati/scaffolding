package com.example.demo.service;

import com.example.demo.domain.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Paths;

@Service
public class ProjetoService {

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private ZipService zipService;

    public Resource projeto(Projeto projeto) throws Exception {
        try {
            File file = arquivoService.buscarArquivo("JAVA");

            if (file != null) {
                percorrerArquivos(file);
            }

            String projetoZip = zipService.zip(file.getAbsolutePath());

            Resource resource = new UrlResource(Paths.get(projetoZip).toUri());
            return resource;
        } catch (MalformedURLException e) {
            throw new Exception("Problemas ao gerar projeto ", e);
        }
    }

    private void percorrerArquivos(File pastaAtual) {
        for (File arquivoAtual: pastaAtual.listFiles()) {
            System.out.println(arquivoAtual.getName());
            if (arquivoAtual.listFiles() != null) {
                percorrerArquivos(arquivoAtual);
            }
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
