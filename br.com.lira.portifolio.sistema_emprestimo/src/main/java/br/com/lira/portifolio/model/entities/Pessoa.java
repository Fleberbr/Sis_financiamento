package br.com.lira.portifolio.model.entities;

public abstract class Pessoa {

    private String nome;
    private String telefone;

    public Pessoa(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }
    public String getNome() {
        return nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public abstract double percentualImposto();


}
