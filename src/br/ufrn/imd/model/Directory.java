package br.ufrn.imd.model;

import java.io.File;

/**
 * Classe que representa um diretório.
 */
public class Directory {
    private String name;
    private File directory;

    /**
     * Construtor que recebe o nome do diretório.
     *
     * @param nome O nome do diretório.
     */
    public Directory(String name) {
        this.name = name;
        directory = new File(name);
    }

    /**
     * Construtor padrão que cria um diretório com nome padrão "output".
     */
    public Directory() {
        name = "output";
        directory = new File(name);
    }

    /**
     * Obtém o nome do diretório.
     *
     * @return O nome do diretório.
     */
    public String getName() {
        return name;
    }

    /**
     * Verifica se o diretório é válido.
     *
     * @return true se o diretório é válido, false caso contrário.
     */
    public boolean isValid() {
        if (!directory.exists()) {
            if (directory.mkdir()) {
                // Cria o diretório
                return true;
            } else {
                // Falha ao criar o diretório
                return false;
            }
        } else {
            // O diretório já existe
            return true;
        }
    }
}
