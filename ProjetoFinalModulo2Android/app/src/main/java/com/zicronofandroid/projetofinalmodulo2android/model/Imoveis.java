package com.zicronofandroid.projetofinalmodulo2android.model;


public class Imoveis {
    int id;
    String descricao;
    String tipologia;
    String localizacao;
    String url_foto;

    public Imoveis(){}

    public Imoveis(String url_foto, String descricao, String localizacao, String tipologia){
        this.url_foto = url_foto;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.tipologia = tipologia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
}

