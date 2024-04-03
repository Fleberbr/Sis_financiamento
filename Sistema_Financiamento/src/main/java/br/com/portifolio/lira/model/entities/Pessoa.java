package br.com.portifolio.lira.model.entities;

import br.com.portifolio.lira.model.enums.TipoPessoa;

public abstract class Pessoa {

    private String nome;
    private String telefone;
    private TipoPessoa tipoPessoa;
    private String id;


    public Pessoa(String nome, String telefone, TipoPessoa tipoPessoa, String id) {
        this.nome = nome;
        this.telefone = telefone;
        this.tipoPessoa = tipoPessoa;
        this.id = id;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public abstract double percentualImposto();

    public abstract String formatarDadosPessoaInsercaoArquivo();
}
