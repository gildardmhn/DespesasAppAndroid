package com.codinginflow.despesas;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "despesa_table")
public class Despesa {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;
    private String descricao;
    private String tipo;
    private Double preco;

    public Despesa(String titulo, String descricao, String tipo, Double preco) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.preco = preco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
}
