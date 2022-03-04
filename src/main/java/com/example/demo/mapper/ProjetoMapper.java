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
                nomeProjetoFinal.append(StringUtils.capitalize(nomeProjeto));
            }

            return nomeProjetoFinal.toString();
        }

        return valor;
    }

    private static String getGrupo(String value) {
        return minusculo(substituirCaracteresEspeciaisMenosPonto(value));
    }

    private static String getArtefato(String value) {
        return minusculo(substituirCaracteresEspeciaisMenosPonto(value));
    }

    private static String getNome(String value) {
        return minusculo(substituirCaracteresEspeciaisMenosTraco(value));
    }

    private static String substituirCaracteresEspeciaisMenosPonto(String value) {
        return value.replaceAll(PATTERN_ONLY_STRINGS_AND_DOT, "");
    }

    private static String substituirCaracteresEspeciaisMenosTraco(String value) {
        return value.replaceAll(PATTERN_ONLY_STRINGS_AND_DASH, "");
    }

    private static String minusculo(String value) {
        return value.toLowerCase(Locale.ROOT);
    }
}
