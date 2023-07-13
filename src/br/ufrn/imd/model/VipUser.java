package br.ufrn.imd.model;

import java.util.ArrayList;


/**
 * Classe que representa um usuário VIP, que estende a classe Usuario.
 */
public class VipUser extends User {

    private ArrayList<Playlist> PlaylistList;

    /**
     * Construtor da classe UsuarioVIP.
     *
     * @param username O nome de usuário do usuário VIP.
     * @param senha    A senha do usuário VIP.
     */
    public VipUser(String username, String password) {
        super(username, password);
        PlaylistList = new ArrayList<>();
    }

    /**
     * Obtém a lista de playlists do usuário VIP.
     *
     * @return A lista de playlists do usuário VIP.
     */
    public ArrayList<Playlist> getPlaylists() {
        return PlaylistList;
    }

    /**
     * Adiciona uma nova playlist à lista de playlists do usuário VIP.
     */
    public void addPlaylist(Playlist playlist) {
        // Implemente a lógica para adicionar uma nova playlist
    	PlaylistList.add(playlist);
    }

    /**
     * Exclui uma playlist da lista de playlists do usuário VIP.
     */
    public void removePlaylist(Playlist playlist) {
        PlaylistList.remove(playlist);
    }
}
