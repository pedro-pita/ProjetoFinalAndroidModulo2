package com.zicronofandroid.projetofinalmodulo2android.model;


public class Clientes {
    int id;
    String nome;
    String idade;
    String url_foto;

    //construtores
    public Clientes(){}

    public Clientes(String nome, String idade, String url_foto){
        this.nome=nome;
        this.idade=idade;
        this.url_foto=url_foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }
}
