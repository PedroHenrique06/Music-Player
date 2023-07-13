package br.ufrn.imd.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma playlist de músicas.
 */
public class Playlist {
    private String title;
    private List<Song> songList;

    /**
     * Construtor da classe Playlist.
     *
     * @param titulo O título da playlist.
     */
    public Playlist(String title) {
        this.title = title;
        this.songList = new ArrayList<>();
    }

    /**
     * Obtém o título da playlist.
     *
     * @return O título da playlist.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Obtém a lista de músicas da playlist.
     *
     * @return A lista de músicas da playlist.
     */
    public List<Song> getSongList() {
        return songList;
    }

    /**
     * Obtém uma versão observável da lista de músicas da playlist.
     * Isso permite a vinculação direta com componentes gráficos do JavaFX.
     *
     * @return Uma lista observável de músicas da playlist.
     */
    public ObservableList<Song> getObservableSongList() {
        return FXCollections.observableList(songList);
    }

    /**
     * Adiciona uma música à playlist.
     *
     * @param musica A música a ser adicionada.
     */
    public void addSong(Song song) {
        songList.add(song);
    }
}
