package br.ufrn.imd.model;

import java.util.ArrayList;

public class Playlist {
	private ArrayList<Musica> listaMusicas = new ArrayList<>();
	private String titulo;
	
	public ArrayList<Musica> getListaMusicas() {
		return listaMusicas;
	}
	public void setListaMusicas(ArrayList<Musica> listaMusicas) {
		this.listaMusicas = listaMusicas;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void addMusica(Musica musica) {
		
	}
	
	public void removeMusica() {
		
	}
}
