package br.ufrn.imd.model;

import java.io.File;

/**
 * Classe que representa um diretório.
 */
public class Diretorio {
    private String nome;
    private File diretorio;

    /**
     * Construtor que recebe o nome do diretório.
     *
     * @param nome O nome do diretório.
     */
    public Diretorio(String nome) {
        this.nome = nome;
        diretorio = new File(nome);
    }

    /**
     * Construtor padrão que cria um diretório com nome padrão "output".
     */
    public Diretorio() {
        nome = "output";
        diretorio = new File(nome);
    }

    /**
     * Obtém o nome do diretório.
     *
     * @return O nome do diretório.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Verifica se o diretório é válido.
     *
     * @return true se o diretório é válido, false caso contrário.
     */
    public boolean ehValido() {
        if (!diretorio.exists()) {
            if (diretorio.mkdir()) {
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
