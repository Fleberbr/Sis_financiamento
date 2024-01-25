package br.com.br.portifolio.model.entities;


import br.com.lira.portifolio.model.entities.Pessoa;
import br.com.lira.portifolio.model.entities.PessoaFisica;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PessoaFisicaTest {



    Pessoa pessoaFisica;

    @BeforeEach // executa antes dos testes
    public void configuracaoInicial() {
        this.pessoaFisica = new PessoaFisica("Elton","11971","1","111");
    }

    @Test
    public void testeCapturarPercentuaImposto(){
        double percentual = this.pessoaFisica.percentualImposto();

        Assertions.assertEquals(10.00,percentual);
    }
}
