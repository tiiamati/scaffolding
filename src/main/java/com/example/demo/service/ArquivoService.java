package com.example.demo.service;

import com.example.demo.domain.ParametroEnum;
import com.example.demo.domain.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.commons.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ArquivoService {

    @Autowired
    private PastaService pastaService;

    public void criarArquivos(File projetoReferencia, Path novoProjeto, Projeto projeto) throws Exception {
        for (File arquivoAtualProjetoReferencia: projetoReferencia.listFiles()) {

            // pega o nome do arquivo
            final String nomeArquivo = arquivoAtualProjetoReferencia.getName();

            // verifica se esse arquivo esta mapeado para ser alterado
            Optional<ParametroEnum> variavelParaRenomear = ParametroEnum.buscarVariavelParaRenomear(nomeArquivo);

            // sempre vai entrar nesse if quando o arquivo for uma pasta com arquivos dentro, lembrando, pastas tambem são arquivos.
            if (pastaService.possuiArquivos(arquivoAtualProjetoReferencia)) {
                novoProjeto = criarEstruturaPasta(novoProjeto, arquivoAtualProjetoReferencia, projeto);
            } else {
                // somente quando não for uma pasta
                copiarArquivoParaNovoDiretorio(arquivoAtualProjetoReferencia, novoProjeto);
                renomearArquivo(variavelParaRenomear, arquivoAtualProjetoReferencia, projeto, novoProjeto);
            }
        }
    }

    private Path criarEstruturaPasta(Path novoProjeto, File arquivoAtualProjetoReferencia, Projeto projeto) throws Exception {

        novoProjeto = pastaService.criarCaminhoLogicoPasta(novoProjeto, arquivoAtualProjetoReferencia);
        pastaService.criarPasta(novoProjeto);

        // recursividade
        criarArquivos(arquivoAtualProjetoReferencia, novoProjeto, projeto);

        // sim isso é necessário
        return pastaService.voltarPastaAnterior(novoProjeto);
    }

    private void copiarArquivoParaNovoDiretorio(File arquivoAtualProjetoReferencia, Path novoProjeto) throws IOException {
        FileUtils.copyFileToDirectory(arquivoAtualProjetoReferencia, novoProjeto.toFile());
    }

    public void renomearArquivo(Optional<ParametroEnum> isVariavelRenomear, File arquivoReferencia,
                                Projeto projeto, Path arquivoNovo) throws IOException {
        if (isVariavelRenomear.isPresent()) {
            String nomeCorretoArquivo = corrigirNomeArquivo(arquivoReferencia, projeto);
            Path source = buscarArquivoAtualDentroDoNovoProjeto(arquivoNovo, arquivoReferencia);
            moverArquivo(source, nomeCorretoArquivo);
        }
    }

    private String corrigirNomeArquivo(File arquivo, Projeto projeto) {
        return projeto.substituirValorParametro(arquivo.getName());
    }

    private Path buscarArquivoAtualDentroDoNovoProjeto(Path arquivoNovo, File arquivoReferencia) {
        StringBuilder caminho = new StringBuilder();

        caminho.append(arquivoNovo.toAbsolutePath())
                .append("\\")
                .append(arquivoReferencia.getName());

        return Paths.get(caminho.toString());
    }

    private void moverArquivo(Path source, String nomeArquivo) throws IOException {
        Files.move(source, source.resolveSibling(nomeArquivo));
    }

}
