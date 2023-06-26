package br.ufrn.imd.model;

import java.util.ArrayList;

/**
 * Classe que representa um usuário VIP, que estende a classe Usuario.
 */
public class UsuarioVIP extends Usuario {

    private ArrayList<Playlist> listaPlaylists;

    /**
     * Construtor da classe UsuarioVIP.
     *
     * @param username O nome de usuário do usuário VIP.
     * @param senha    A senha do usuário VIP.
     */
    public UsuarioVIP(String username, String senha) {
        super(username, senha);
        listaPlaylists = new ArrayList<>();
    }

    /**
     * Obtém a lista de playlists do usuário VIP.
     *
     * @return A lista de playlists do usuário VIP.
     */
    public ArrayList<Playlist> getListaPlaylists() {
        return listaPlaylists;
    }

    /**
     * Adiciona uma nova playlist à lista de playlists do usuário VIP.
     */
    public void addPlaylist(Playlist playlist) {
        // Implemente a lógica para adicionar uma nova playlist
    	listaPlaylists.add(playlist);
    }

    /**
     * Exclui uma playlist da lista de playlists do usuário VIP.
     */
    public void excludePlaylist(Playlist playlist) {
        listaPlaylists.remove(playlist);
    }
}
