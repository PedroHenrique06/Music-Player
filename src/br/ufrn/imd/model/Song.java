package br.ufrn.imd.model;

import java.io.File;

/**
 * Classe que representa uma música.
 */
public class Song {
    private String title;
    private Boolean playing;
    private Boolean selected;
    private String local;
    private File file;

    /**
     * Obtém o título da música.
     *
     * @return O título da música.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define o título da música.
     *
     * @param titulo O título da música.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Verifica se a música está tocando.
     *
     * @return true se a música está tocando, false caso contrário.
     */
    public Boolean getPLaying() {
        return playing;
    }

    /**
     * Define se a música está tocando.
     *
     * @param tocando true se a música está tocando, false caso contrário.
     */
    public void setPLaying(Boolean playing) {
        this.playing = playing;
    }

    /**
     * Verifica se a música está selecionada.
     *
     * @return true se a música está selecionada, false caso contrário.
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     * Define se a música está selecionada.
     *
     * @param selecionado true se a música está selecionada, false caso contrário.
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
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
    public File getFile() {
        return file;
    }

    /**
     * Define o arquivo da música.
     *
     * @param arquivo O arquivo da música.
     */
    public void setArquivo(File file) {
        this.file = file;
    }
}
