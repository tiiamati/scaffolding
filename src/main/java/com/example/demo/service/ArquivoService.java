package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.commons.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class ArquivoService {

    @Autowired
    private PastaService pastaService;

    public void criarArquivos(File projetoReferencia, Path novoProjeto) throws Exception {
        for (File arquivoAtualProjetoReferencia: projetoReferencia.listFiles()) {

            // sempre vai entrar nesse if quando o arquivo for uma pasta com arquivos dentro, lembrando, pastas tambem são arquivos.
            if (pastaService.possuiArquivos(arquivoAtualProjetoReferencia)) {
                novoProjeto = criarEstruturaPasta(novoProjeto, arquivoAtualProjetoReferencia);
            } else {
                // somente quando não for uma pasta
                copiarArquivoParaNovoDiretorio(arquivoAtualProjetoReferencia, novoProjeto);
            }
        }
    }

    private Path criarEstruturaPasta(Path novoProjeto, File arquivoAtualProjetoReferencia) throws Exception {

        novoProjeto = pastaService.criarCaminhoLogicoPasta(novoProjeto, arquivoAtualProjetoReferencia);
        pastaService.criarPasta(novoProjeto);

        // recursividade
        criarArquivos(arquivoAtualProjetoReferencia, novoProjeto);

        // sim isso é necessário
        return pastaService.voltarPastaAnterior(novoProjeto);
    }

    private void copiarArquivoParaNovoDiretorio(File arquivoAtualProjetoReferencia, Path novoProjeto) throws IOException {
        FileUtils.copyFileToDirectory(arquivoAtualProjetoReferencia, novoProjeto.toFile());
    }

}
