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
    private ZipService zipService;

    @Autowired
    private PastaService pastaService;

    @Autowired
    private ArquivoService arquivoService;

    public Resource gerarProjeto(Projeto projeto) throws Exception {
        try {
            File projetoReferencia = pastaService.buscarPasta(GeradorProjetoConst.PATH_JAVA);

            if (projetoReferencia != null) {

                // cria o caminho raiz do projeto
                Path pastaRaizNovoProjeto = Path.of(buscarNomeProjeto(projeto));
                Path novoProjeto = pastaService.criarPastaRaizProjeto(pastaRaizNovoProjeto, projeto.nome);

                // percorre o projeto de referência
                arquivoService.criarArquivos(projetoReferencia, novoProjeto, projeto);

                String projetoZip = zipService.zip(novoProjeto.toString());

                return new UrlResource(Paths.get(projetoZip).toUri());
            }
            return null;
        } catch (MalformedURLException e) {
            throw new Exception("Problemas ao gerar projeto ", e);
        }
    }

    private String buscarNomeProjeto(Projeto projeto) {
        return GeradorProjetoConst.PROJETO_REFERENCIA.concat(projeto.nome);
    }

    public String header(Resource resource) {
        StringBuilder header = new StringBuilder();

        header.append("attachment; filename=\"")
                .append(resource.getFilename())
                .append("\"");

        return header.toString();
    }
}
