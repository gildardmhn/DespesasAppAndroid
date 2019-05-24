package com.codinginflow.despesas.Model;

public class Estabelecimento {

    private String hash;
    private String nome;
    private String endereco;
    private String telefone;

    public Estabelecimento(String nome, String endereco, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Estabelecimento() {
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
