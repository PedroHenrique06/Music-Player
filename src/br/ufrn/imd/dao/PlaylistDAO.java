package br.ufrn.imd.dao;

import java.util.ArrayList;

import br.ufrn.imd.model.Playlist;

/**
 * Classe DAO (Data Access Object) para a manipulação de playlists.
 * Responsável por gerenciar a lista de playlists e realizar operações de adição e remoção.
 */
public class PlaylistDAO {
    private static PlaylistDAO instance;
    private ArrayList<Playlist> listaPlaylists;

    /**
     * Construtor privado da classe PlaylistDAO.
     * Inicializa a lista de playlists.
     */
    private PlaylistDAO() {
        listaPlaylists = new ArrayList<>();
    }

    /**
     * Obtém a instância única da classe PlaylistDAO (implementação do padrão Singleton).
     *
     * @return A instância única da classe PlaylistDAO.
     */
    public static PlaylistDAO getInstance() {
        if (instance == null) {
            instance = new PlaylistDAO();
        }
        return instance;
    }

    /**
     * Reseta a instância. 
     */
    public void resetDAO() {
        instance = null;
        // Limpar os dados ou realizar outras ações necessárias
    }
    /**
     * Adiciona uma playlist à lista de playlists.
     *
     * @param playlist A playlist a ser adicionada.
     */
    public void adicionarPlaylist(Playlist playlist) {
        listaPlaylists.add(playlist);
    }

    /**
     * Remove uma playlist da lista de playlists.
     *
     * @param playlist A playlist a ser removida.
     */
    public void removerPlaylist(Playlist playlist) {
        listaPlaylists.remove(playlist);
    }

    /**
     * Obtém a lista de playlists.
     *
     * @return A lista de playlists.
     */
    public ArrayList<Playlist> getListaPlaylists() {
        return listaPlaylists;
    }
}
