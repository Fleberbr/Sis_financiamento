package br.com.br.portifolio.model.entities;


import br.com.portifolio.lira.model.entities.Pessoa;
import br.com.portifolio.lira.model.entities.PessoaJuridica;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PessoaJuridicaTest {

    Pessoa pessoa;

    @BeforeEach // executa antes dos testes
    public void configuracaoInicial() {
        pessoa = new PessoaJuridica("Elton","11971","1","111");
    }


    @Test
    public void testeCapturarPercentuaImposto(){
        double percentual = pessoa.percentualImposto();

        Assertions.assertEquals(5.00,percentual);
    }

    @Test
    public void testeToString(){

        Assertions.assertEquals(" Cnpj: 1 Nome: Elton Telefone: 11971 Inscrição municipal: 111", pessoa.toString() );
    }
}
