package br.ufrn.imd.model;

import java.util.ArrayList;

public class UsuarioVIP extends Usuario {

	
	public UsuarioVIP(String username, String senha) {
		super(username, senha);
		// TODO Auto-generated constructor stub
	}

ArrayList<Playlist> listaPlaylists = new ArrayList<>();



public ArrayList<Playlist> getListaPlaylists() {
	return listaPlaylists;
}

public void addPlaylist() {
	
}

public void excludePlaylist() {
	
}

}
