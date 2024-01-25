package br.com.lira.portifolio.model.entities;

public class PessoaFisica extends Pessoa{

    private String id;
    private String tituloEleitor;

    public PessoaFisica(String nome, String telefone, String id, String tituloEleitor) {
        super(nome, telefone);
        this.id = id;
        this.tituloEleitor = tituloEleitor;
    }

    public String getid() {
        return id;
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
        return " Cpf: " + id +
               " Nome: " + getNome() +
               " Telefone: " + getTelefone()+
               " Titulo de eleitor: " + tituloEleitor ;
    }
}
