package br.ufrn.imd.model;

/**
 * Classe que representa um usuário.
 */
public class User {
    private String name;
    private String id;
    private String username;
    private String password;

    /**
     * Construtor da classe Usuario.
     *
     * @param username O nome de usuário do usuário.
     * @param senha    A senha do usuário.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Obtém o nome do usuário.
     *
     * @return O nome do usuário.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do usuário.
     *
     * @param nome O nome do usuário.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém o ID do usuário.
     *
     * @return O ID do usuário.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o ID do usuário.
     *
     * @param id O ID do usuário.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtém o nome de usuário do usuário.
     *
     * @return O nome de usuário do usuário.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define o nome de usuário do usuário.
     *
     * @param username O nome de usuário do usuário.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtém a senha do usuário.
     *
     * @return A senha do usuário.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha do usuário.
     *
     * @param senha A senha do usuário.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
