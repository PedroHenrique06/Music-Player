package br.ufrn.imd.dao;

import java.util.ArrayList;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import br.ufrn.imd.model.Usuario;
import br.ufrn.imd.model.UsuarioVIP;

public class UsuarioDAO {
    private static UsuarioDAO instance;
    private ArrayList<Usuario> listaUsuarios;
    private Usuario usuarioAtual;
    private UsuarioVIP usuarioVIPAtual;

    private UsuarioDAO() {
        listaUsuarios = new ArrayList<>();
    }

    public static UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }
    
    public void setUsuarioAtual(Usuario usuarioAtual ) {
    	this.usuarioAtual = usuarioAtual;
    }
    
    public Usuario getUsuarioAtual() {
    	return usuarioAtual;
    }
    
    public void setUsuarioAtual(UsuarioVIP usuarioVIPAtual ) {
    	this.usuarioVIPAtual = usuarioVIPAtual;
    }
    
    public Usuario getUsuarioVIPAtual() {
    	return usuarioVIPAtual;
    }

    public void carregaUsuarios() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./usuarios/logins.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(" : ");

                if (campos.length == 3) {
                    String nome = campos[0];
                    String senha = campos[1];
                    boolean isVip = campos[2].equals("true");

                    if (isVip) {
                        UsuarioVIP usuarioVIP = new UsuarioVIP(nome, senha);
                        UsuarioDAO.getInstance().adicionarUsuario(usuarioVIP);
                    } else {
                        Usuario usuario = new Usuario(nome, senha);
                        UsuarioDAO.getInstance().adicionarUsuario(usuario);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
