package com.example.demo.domain;

import java.util.Arrays;
import java.util.Optional;

public enum ParametroEnum {
    PROJECT_CLASS_NAME("${projectClassName}"),
    PACKAGE("${package}"),
    GROUP("${group}"),
    ARTIFACT("${artifact}"),
    DESCRIPTION("${description}");

    public String value;

    ParametroEnum(String value) {
        this.value = value;
    }

    public static Optional<ParametroEnum> getParametroEnum(String value) {
        return Arrays.stream(ParametroEnum.values())
                .filter(parametroEnum -> value.contains(parametroEnum.value))
                .findAny();
    }
}
