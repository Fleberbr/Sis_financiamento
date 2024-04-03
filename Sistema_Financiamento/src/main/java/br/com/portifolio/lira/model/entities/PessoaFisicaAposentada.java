package br.com.portifolio.lira.model.entities;

import br.com.portifolio.lira.model.enums.TipoPessoa;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PessoaFisicaAposentada extends PessoaFisica{

    private Date dataAposentadoria;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public PessoaFisicaAposentada(String nome, String telefone, String id, String tituloEleitor, Date dataAposentadoria) {
        super(nome, telefone, id, tituloEleitor, TipoPessoa.PESSOA_FISICA_APOSENTADA);
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
        return  "Cpf: " + getId() +
                " Nome: " + getNome() +
                " Tipo pessoa: " +getTituloEleitor() +
                " Telefone: " + getTelefone() +
                " Titulo de eleitor: " + getTituloEleitor() +
                " Data aposentadoria: " + getDataAposentadoria();
    }

    @Override
    public String formatarDadosPessoaInsercaoArquivo() {
        return  getTipoPessoa()+ ","+
                getId() + "," +
                getNome() + "," +
                getTelefone() + "," +
                getTituloEleitor() + "," +
                formato.format(getDataAposentadoria());
    }
}

