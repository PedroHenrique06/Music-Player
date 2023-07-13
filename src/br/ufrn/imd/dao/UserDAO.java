package br.ufrn.imd.dao;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.ufrn.imd.model.User;
import br.ufrn.imd.model.VipUser;

public class UserDAO {
    private static UserDAO instance;
    private ArrayList<User> userList;
    private User currentUser;

    private UserDAO() {
        userList = new ArrayList<>();
    }

    /**
     * Obtém a instância única da classe UserDAO (implementação do padrão Singleton).
     *
     * @return A instância única da classe UserDAO.
     */
    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }
    
    /**
     * Reseta a instância. 
     */
    public void resetDAO() {
        instance = null;
        // Limpar os dados ou realizar outras ações necessárias
    }
    
    /**
     * Indica qual usuário está sendo utilizado no momento
     * @return usuário atual
     */
    public User getCurrentUser() {
    	return currentUser;
    }
    
    /**
     * Define o usuário atual
     *
     */
    public void setCurrentUser(User currentUser) {
    	this.currentUser = currentUser;
    } 

    /**
     * Carrega os usuários a partir de um arquivo de texto.
     * O arquivo deve estar no formato "nome : senha : isVIP",
     * onde "isVIP" indica se o usuário é VIP (true/false).
     */
    public void loadUsers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./usuarios/logins.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(" : ");

                if (campos.length == 3) {
                    String name = campos[0];
                    String password = campos[1];
                    boolean isVip = campos[2].equals("true");

                    if (isVip) {
                        VipUser vipUser = new VipUser(name, password);
                        UserDAO.getInstance().addUser(vipUser);
                    } else {
                        User user = new User(name, password);
                        UserDAO.getInstance().addUser(user);
                    }
                }
            }
            reader.close();
        } catch (IOException eIO) {
            System.out.println("O arquivo de logins ainda não existe, cadastre um usuário para criá-lo.");
        }
    }
    
    /**
     * Adiciona um usuário à lista de usuários.
     * 
     * @param usuario O usuário a ser adicionado.
     */
    public void addUser(User user) {
        userList.add(user);
    }

    /**
     * Remove um usuário da lista de usuários.
     * 
     * @param usuario O usuário a ser removido.
     */
    public void removeUser(User user) {
        userList.remove(user);
    }
    
    
    /**
     * Retorna a lista de usuários.
     * 
     * @return A lista de usuários.
     */
    public ArrayList<User> getUsersList() {
        return userList;
    }
}
