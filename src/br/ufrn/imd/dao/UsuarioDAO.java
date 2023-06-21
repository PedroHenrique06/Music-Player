package br.ufrn.imd.dao;

import java.util.ArrayList;

import br.ufrn.imd.model.Usuario;

public class UsuarioDAO {
    private static UsuarioDAO instance;
    private ArrayList<Usuario> listaUsuarios;

    private UsuarioDAO() {
        listaUsuarios = new ArrayList<>();
    }

    public static UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    public void adicionarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public void removerUsuario(Usuario usuario) {
        listaUsuarios.remove(usuario);
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
}
