package br.ufrn.imd.dao;

import java.util.ArrayList;

import br.ufrn.imd.model.Musica;

/**
 * Classe DAO (Data Access Object) para a manipulação de músicas.
 * Responsável por gerenciar a lista de músicas e realizar operações de adição e remoção.
 */
public class MusicaDAO {
    private static MusicaDAO instance;
    private ArrayList<Musica> listaMusicas;

    /**
     * Construtor privado da classe MusicaDAO.
     * Inicializa a lista de músicas.
     */
    private MusicaDAO() {
        listaMusicas = new ArrayList<>();
    }

    /**
     * Obtém a instância única da classe MusicaDAO (implementação do padrão Singleton).
     *
     * @return A instância única da classe MusicaDAO.
     */
    public static MusicaDAO getInstance() {
        if (instance == null) {
            instance = new MusicaDAO();
        }
        return instance;
    }

    /**
     * Adiciona uma música à lista de músicas.
     *
     * @param musica A música a ser adicionada.
     */
    public void addMusica(Musica musica) {
        listaMusicas.add(musica);
    }

    /**
     * Remove uma música da lista de músicas.
     *
     * @param musica A música a ser removida.
     */
    public void removeMusica(Musica musica) {
        listaMusicas.remove(musica);
    }
    
    /**
     * Reseta a instância. 
     */
    public void resetDAO() {
        instance = null;
        // Limpar os dados ou realizar outras ações necessárias
    }
    /**
     * Obtém a lista de músicas.
     *
     * @return A lista de músicas.
     */
    public ArrayList<Musica> getListaMusicas() {
        return listaMusicas;
    }
}
