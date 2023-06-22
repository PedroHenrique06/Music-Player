package br.ufrn.imd.dao;

import java.util.ArrayList;

import br.ufrn.imd.model.Musica;

public class MusicaDAO {
    private static MusicaDAO instance;
    private ArrayList<Musica> listaMusicas;

    private MusicaDAO() {
        listaMusicas = new ArrayList<>();
    }

    public static MusicaDAO getInstance() {
        if (instance == null) {
            instance = new MusicaDAO();
        }
        return instance;
    }

    public void addMusica(Musica musica) {
        listaMusicas.add(musica);
    }

    public void removeMusica(Musica musica) {
        listaMusicas.remove(musica);
    }

    public ArrayList<Musica> getListaMusicas() {
        return listaMusicas;
    }
}

