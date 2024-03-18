package br.com.br.portifolio.model.entities;


import br.com.portifolio.lira.model.entities.Pessoa;
import br.com.portifolio.lira.model.entities.PessoaFisica;
import br.com.portifolio.lira.model.enums.TipoPessoa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PessoaFisicaTest {

    Pessoa pessoa;

    @BeforeEach // executa antes dos testes
    public void configuracaoInicial() {
        pessoa = new PessoaFisica("Elton","11971","1","111", TipoPessoa.PESSOA_FISICA);
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

