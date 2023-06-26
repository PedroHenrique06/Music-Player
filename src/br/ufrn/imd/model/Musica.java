package br.ufrn.imd.model;

import java.io.File;

/**
 * Classe que representa uma música.
 */
public class Musica {
    private String titulo;
    private int tamanhoSegundos;
    private Boolean tocando;
    private Boolean selecionado;
    private String local;
    private File arquivo;

    /**
     * Obtém o título da música.
     *
     * @return O título da música.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o título da música.
     *
     * @param titulo O título da música.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtém o tamanho da música em segundos.
     *
     * @return O tamanho da música em segundos.
     */
    public int getTamanhoSegundos() {
        return tamanhoSegundos;
    }

    /**
     * Define o tamanho da música em segundos.
     *
     * @param tamanhoSegundos O tamanho da música em segundos.
     */
    public void setTamanhoSegundos(int tamanhoSegundos) {
        this.tamanhoSegundos = tamanhoSegundos;
    }

    /**
     * Verifica se a música está tocando.
     *
     * @return true se a música está tocando, false caso contrário.
     */
    public Boolean getTocando() {
        return tocando;
    }

    /**
     * Define se a música está tocando.
     *
     * @param tocando true se a música está tocando, false caso contrário.
     */
    public void setTocando(Boolean tocando) {
        this.tocando = tocando;
    }

    /**
     * Verifica se a música está selecionada.
     *
     * @return true se a música está selecionada, false caso contrário.
     */
    public Boolean getSelecionado() {
        return selecionado;
    }

    /**
     * Define se a música está selecionada.
     *
     * @param selecionado true se a música está selecionada, false caso contrário.
     */
    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }

    /**
     * Obtém o local da música.
     *
     * @return O local da música.
     */
    public String getLocal() {
        return local;
    }

    /**
     * Define o local da música.
     *
     * @param local O local da música.
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * Obtém o arquivo da música.
     *
     * @return O arquivo da música.
     */
    public File getArquivo() {
        return arquivo;
    }

    /**
     * Define o arquivo da música.
     *
     * @param arquivo O arquivo da música.
     */
    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }
}
