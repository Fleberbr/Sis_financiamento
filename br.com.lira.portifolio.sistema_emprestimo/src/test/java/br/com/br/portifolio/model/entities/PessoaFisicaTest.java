package br.com.br.portifolio.model.entities;


import br.com.lira.portifolio.model.entities.Pessoa;
import br.com.lira.portifolio.model.entities.PessoaFisica;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PessoaFisicaTest {

    Pessoa pessoa;

    @BeforeEach // executa antes dos testes
    public void configuracaoInicial() {
        pessoa = new PessoaFisica("Elton","11971","1","111");
    }

    @Test
    public void testeCapturarPercentuaImposto(){
        double percentual = this.pessoa.percentualImposto();

        Assertions.assertEquals(10.00,percentual);
    }

    @Test
    public void testeToString(){

        Assertions.assertEquals(" Cpf: 1 Nome: Elton Telefone: 11971 Titulo de eleitor: 111",pessoa.toString() );
    }
}

