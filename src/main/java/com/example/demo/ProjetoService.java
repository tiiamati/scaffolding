package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;

@Service
public class ProjetoService {

    @Autowired
    private ArquivoService arquivoService;

    public Resource projeto(Projeto projeto) throws Exception {
        try {
            File file = arquivoService.buscarArquivo("teste.txt");

            arquivoService.escrever(file, projeto);

            Resource resource = new UrlResource(file.toPath().toUri());
            return resource;
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
