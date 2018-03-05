package com.zicronofandroid.projetofinalmodulo2android.model;

public class Users {

    int id;
    String login;
    String nome;
    String password;
    String level;

    public Users() {
    }

    public Users(String login, String nome, String password, String level) {
        this.login = login;
        this.nome = nome;
        this.password = password;
        this.level = level;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
