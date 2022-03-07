package com.example.demo.utils;

import java.io.*;

public class ArquivoUtils {

    public static void escrever(File arquivo, String conteudo) throws Exception {
        try {
            FileWriter fileWriter = new FileWriter(arquivo);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(conteudo);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            throw new Exception("Não foi possível escrever no arquivo", e);
        }
    }

    public static String ler(File arquivo) throws Exception {
        try {
            FileReader fileReader = new FileReader(arquivo);
            BufferedReader reader = new BufferedReader(fileReader);

            String line = reader.readLine();

            StringBuilder conteudo = new StringBuilder();

            while (line != null) {
                conteudo.append(line)
                        .append(System.lineSeparator());
                line = reader.readLine();
            }

            reader.close();

            return conteudo.toString();

        } catch (IOException e) {
            throw new Exception("Não foi possível ler o arquivo", e);
        }
    }
}
