package br.ufrn.imd.model;

/**
 * Classe que representa um usuário.
 */
public class Usuario {
    private String nome;
    private String id;
    private String username;
    private String senha;
    private Boolean logado;

    /**
     * Construtor da classe Usuario.
     *
     * @param username O nome de usuário do usuário.
     * @param senha    A senha do usuário.
     */
    public Usuario(String username, String senha) {
        this.username = username;
        this.senha = senha;
        logado = false;
    }

    /**
     * Obtém o nome do usuário.
     *
     * @return O nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário.
     *
     * @param nome O nome do usuário.
     */
    public void setNome(String nome) {
        this.nome = nome;
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
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     *
     * @param senha A senha do usuário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Verifica se o usuário está logado.
     *
     * @return true se o usuário está logado, false caso contrário.
     */
    public Boolean getLogado() {
        return logado;
    }

    /**
     * Define o status de logado do usuário.
     *
     * @param logado O status de logado do usuário.
     */
    public void setLogado(Boolean logado) {
        this.logado = logado;
    }
}
