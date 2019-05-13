package com.codinginflow.despesas;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuario_table")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nome;
    private String email;
    private String telefone;
    private String senha;

    public Usuario(String nome, String email, String telefone, String senha) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
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

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSenha() {
        return senha;
    }
}
