package com.codinginflow.despesas.Model;

import androidx.room.Ignore;

public class Despesa {

    private String hash;
    private String titulo;
    private String descricao;
    private String tipo;
    private Double preco;
    private String uidUsuario;

    public Despesa(String titulo, String descricao, String tipo, Double preco) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.preco = preco;
    }

    @Ignore
    public Despesa() {
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getPreco() {
        return preco;
    }

    public String getUidUsuario() {
        return uidUsuario;
    }

    public void setUidUsuario(String uidUsuario) {
        this.uidUsuario = uidUsuario;
    }
}
