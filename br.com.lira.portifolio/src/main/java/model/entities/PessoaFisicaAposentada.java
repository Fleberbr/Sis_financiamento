package model.entities;

public class PessoaFisicaAposentada extends PessoaFisica{

    private String dataAposentadoria;

    public PessoaFisicaAposentada(String nome, String telefone, String id, String tituloEleitor, String dataAposentadoria) {
        super(nome, telefone, id, tituloEleitor);
        this.dataAposentadoria = dataAposentadoria;
    }

    public String getDataAposentadoria() {
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

