package br.com.portifolio.lira.model.entities;

import br.com.portifolio.lira.model.enums.TipoPessoa;

public class PessoaFisica extends Pessoa{

    private String tituloEleitor;

    public PessoaFisica(String nome, String telefone, String id, String tituloEleitor, TipoPessoa tipoPessoa) {
        super(nome, telefone,  tipoPessoa, id);
       this.tituloEleitor = tituloEleitor;
    }

    public PessoaFisica(String nome, String telefone, String id, String tituloEleitor) {
        super(nome, telefone, TipoPessoa.PESSOA_FISICA, id);

        this.tituloEleitor = tituloEleitor;
    }

    public String getTituloEleitor() {
        return tituloEleitor;
    }

    @Override
    public double percentualImposto() {
        return 10.0;
    }

    @Override
    public String toString() {
        return "Cpf: " + getId()+
               " Nome: " + getNome() +
               " Tipo pessoa: " + getTipoPessoa()+
               " Telefone: " + getTelefone()+
               " Titulo de eleitor: " + tituloEleitor ;
    }
    @Override
    public String formatarDadosPessoaInsercaoArquivo() {
        return  getTipoPessoa()+ ","+
                getId() + "," +
                getNome() + "," +
                getTelefone() + "," +
                getTituloEleitor()  + "," ;
    }
}
