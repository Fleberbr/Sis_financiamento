package br.com.portifolio.lira.model.entities;

import br.com.portifolio.lira.model.enums.TipoPessoa;

public class PessoaJuridica extends Pessoa{

    private String inscricaoMunicipal;

    public PessoaJuridica(String nome, String telefone,String id, String inscricaoMunicipal) {
        super(nome, telefone, TipoPessoa.PESSOA_JURIDICA, id);
        this.inscricaoMunicipal = inscricaoMunicipal;
    }


    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    @Override
    public double percentualImposto() {
        return 5.0;
    }

    @Override
    public String toString() {
        return  "Cnpj: " + getId()+
                " Nome: " + getNome()+
                " Tipo pessoa: " +getTipoPessoa()+
                " Telefone: " + getTelefone()+
                " Inscrição municipal: " + inscricaoMunicipal ;
    }
    @Override
    public String formatarDadosPessoaInsercaoArquivo() {
        return  getTipoPessoa()+ "," +
                getId() + "," +
                getNome() + "," +
                getTelefone() + "," +
                getInscricaoMunicipal()  + "," ;
    }
}
