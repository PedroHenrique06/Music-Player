package br.ufrn.imd.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String titulo;
    private List<Musica> listaMusicas;

    public Playlist(String titulo) {
        this.titulo = titulo;
        this.listaMusicas = new ArrayList<>();
	
    }    
        

    public String getTitulo() {
        return titulo;
    }

    public List<Musica> getListaMusicas() {
        return listaMusicas;
    }

    public ObservableList<Musica> getObservableListaMusicas() {
        return FXCollections.observableList(listaMusicas);
    }

    public void addMusica(Musica musica) {
        listaMusicas.add(musica);
    }
}
