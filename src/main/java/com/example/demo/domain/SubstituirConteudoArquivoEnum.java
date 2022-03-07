package com.example.demo.domain;

public enum SubstituirConteudoArquivoEnum {
    APPLICATION_JAVA("${projectNameClass}Application.java"),
    APPLICATION_JAVA_TESTE("${projectNameClass}ApplicationTests.java"),
    POM("pom.xml");

    public String value;

    SubstituirConteudoArquivoEnum(String value) {
        this.value = value;
    }
}