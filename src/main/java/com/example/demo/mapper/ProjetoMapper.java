package com.example.demo.mapper;

import com.example.demo.domain.Projeto;
import lombok.Builder;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Builder
public class ProjetoMapper {

    private static final String PATTERN_ONLY_STRINGS_AND_DASH = "[^a-zA-Z -]+";
    private static final String PATTERN_ONLY_STRINGS_AND_DOT = "[^a-zA-Z .]+";

    public static Projeto toModel(String grupo, String artefato, String nome, String descricao) {
        String nomeGrupo = getGrupo(grupo);
        String nomeArtefato = getArtefato(artefato);

        return Projeto.builder()
                .grupo(nomeGrupo)
                .artefato(nomeArtefato)
                .nome(getNome(nome))
                .descricao(descricao)
                .nomePacote(nomeGrupo.concat(".").concat(nomeArtefato))
                .nomeProjetoClasse(getNomeProjetoClasse(nome))
                .build();
    }

    private static String getNomeProjetoClasse(String valor) {
        valor = substituirCaracteresEspeciaisMenosTraco(valor);

        if (valor.contains("-")) {
            String[] nomeProjetoArray = valor.split("-");

            StringBuilder nomeProjetoFinal = new StringBuilder();

            for (String nomeProjeto : nomeProjetoArray) {
                nomeProjetoFinal.append(primeiraLetraMaiuscula(nomeProjeto));
            }

            return nomeProjetoFinal.toString();
        }

        return primeiraLetraMaiuscula(valor);
    }

    private static String getGrupo(String valor) {
        return minusculo(substituirCaracteresEspeciaisMenosPonto(valor));
    }

    private static String getArtefato(String valor) {
        return minusculo(substituirCaracteresEspeciaisMenosPonto(valor));
    }

    private static String getNome(String valor) {
        return minusculo(substituirCaracteresEspeciaisMenosTraco(valor));
    }

    private static String substituirCaracteresEspeciaisMenosPonto(String valor) {
        return valor.replaceAll(PATTERN_ONLY_STRINGS_AND_DOT, "");
    }

    private static String substituirCaracteresEspeciaisMenosTraco(String valor) {
        return valor.replaceAll(PATTERN_ONLY_STRINGS_AND_DASH, "");
    }

    private static String minusculo(String valor) {
        return valor.toLowerCase(Locale.ROOT);
    }

    private static String primeiraLetraMaiuscula(String valor) {
        return StringUtils.capitalize(valor);
    }
}
