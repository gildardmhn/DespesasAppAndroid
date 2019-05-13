package com.codinginflow.despesas;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "estabelecimento_table")
public class Estabelecimento {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nome;
    private String endereco;
    private String telefone;

    public Estabelecimento(String nome, String endereco, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }
}
