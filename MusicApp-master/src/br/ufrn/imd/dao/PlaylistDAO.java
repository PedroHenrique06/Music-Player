package br.ufrn.imd.dao;

import java.util.ArrayList;

import br.ufrn.imd.model.Playlist;

public class PlaylistDAO {
    private static PlaylistDAO instance;
    private ArrayList<Playlist> listaPlaylists;

    private PlaylistDAO() {
        listaPlaylists = new ArrayList<>();
    }

    public static PlaylistDAO getInstance() {
        if (instance == null) {
            instance = new PlaylistDAO();
        }
        return instance;
    }

    public void adicionarPlaylist(Playlist playlist) {
        listaPlaylists.add(playlist);
    }

    public void removerPlaylist(Playlist playlist) {
        listaPlaylists.remove(playlist);
    }

    public ArrayList<Playlist> getListaPlaylists() {
        return listaPlaylists;
    }
}

