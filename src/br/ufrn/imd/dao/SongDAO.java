package br.ufrn.imd.dao;

import java.util.ArrayList;

import br.ufrn.imd.model.Song;

/**
 * Classe DAO (Data Access Object) para a manipulação de músicas.
 * Responsável por gerenciar a lista de músicas e realizar operações de adição e remoção.
 */
public class SongDAO {
    private static SongDAO instance;
    private ArrayList<Song> songList;

    /**
     * Construtor privado da classe MusicaDAO.
     * Inicializa a lista de músicas.
     */
    private SongDAO() {
        songList = new ArrayList<>();
    }

    /**
     * Obtém a instância única da classe SongDAO (implementação do padrão Singleton).
     *
     * @return A instância única da classe SongDAO.
     */
    public static SongDAO getInstance() {
        if (instance == null) {
            instance = new SongDAO();
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
     * Adiciona uma música à lista de músicas.
     *
     * @param musica A música a ser adicionada.
     */
    public void addSong(Song song) {
        songList.add(song);
    }

    /**
     * Remove uma música da lista de músicas.
     *
     * @param musica A música a ser removida.
     */
    public void removeSong(Song song) {
        songList.remove(song);
    }
    
    
    /**
     * Obtém a lista de músicas.
     *
     * @return A lista de músicas.
     */
    public ArrayList<Song> getSongList() {
        return songList;
    }
}
