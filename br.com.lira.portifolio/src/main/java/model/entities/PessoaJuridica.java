package model.entities;

public class PessoaJuridica extends Pessoa{

    private String id;
    private String inscricaoMunicipal;

    public PessoaJuridica(String nome, String telefone, String id, String inscricaoMunicipal) {
        super(nome, telefone);
        this.id = id;
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getId() {
        return id;
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
        return  " Cnpj:" + id +
                " Nome: " + getNome()+
                " Telefone: " + getTelefone()+
                " Inscrição municipal: " + inscricaoMunicipal ;
    }
}
