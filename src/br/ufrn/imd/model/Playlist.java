package br.ufrn.imd.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma playlist de músicas.
 */
public class Playlist {
    private String titulo;
    private List<Musica> listaMusicas;

    /**
     * Construtor da classe Playlist.
     *
     * @param titulo O título da playlist.
     */
    public Playlist(String titulo) {
        this.titulo = titulo;
        this.listaMusicas = new ArrayList<>();
    }

    /**
     * Obtém o título da playlist.
     *
     * @return O título da playlist.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Obtém a lista de músicas da playlist.
     *
     * @return A lista de músicas da playlist.
     */
    public List<Musica> getListaMusicas() {
        return listaMusicas;
    }

    /**
     * Obtém uma versão observável da lista de músicas da playlist.
     * Isso permite a vinculação direta com componentes gráficos do JavaFX.
     *
     * @return Uma lista observável de músicas da playlist.
     */
    public ObservableList<Musica> getObservableListaMusicas() {
        return FXCollections.observableList(listaMusicas);
    }

    /**
     * Adiciona uma música à playlist.
     *
     * @param musica A música a ser adicionada.
     */
    public void addMusica(Musica musica) {
        listaMusicas.add(musica);
    }
}
