package model.entities;

import java.util.Date;

public class PessoaFisicaAposentada extends PessoaFisica{

    private Date dataAposentadoria;

    public PessoaFisicaAposentada(String nome, String telefone, String id, String tituloEleitor, Date dataAposentadoria) {
        super(nome, telefone, id, tituloEleitor);
        this.dataAposentadoria = dataAposentadoria;
    }

    public Date getDataAposentadoria() {
        return dataAposentadoria;
    }

    @Override
    public double percentualImposto() {
        return 3.0;
    }

    @Override
    public String toString() {
        return  " Cpf: " + getid() +
                " Nome: " + getNome() +
                " Telefone: " + getTelefone()+
                " Titulo de eleitor: " + getTituloEleitor() +
                " Data aposentadoria: " + getDataAposentadoria();
    }
}

