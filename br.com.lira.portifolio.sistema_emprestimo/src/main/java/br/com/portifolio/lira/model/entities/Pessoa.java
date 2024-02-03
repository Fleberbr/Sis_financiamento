package br.com.portifolio.lira.model.entities;

import br.com.portifolio.lira.model.enums.TipoPessoa;

public abstract class Pessoa {

    private String nome;
    private String telefone;
    private TipoPessoa tipoPessoa;


    public Pessoa(String nome, String telefone,TipoPessoa tipoPessoa) {
        this.nome = nome;
        this.telefone = telefone;
        this.tipoPessoa = tipoPessoa;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
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
