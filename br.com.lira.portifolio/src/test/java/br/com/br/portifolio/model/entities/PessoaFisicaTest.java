package br.com.br.portifolio.model.entities;

import br.com.lira.portifolio.model.entities.Emprestimo;
import br.com.lira.portifolio.model.entities.Pessoa;
import br.com.lira.portifolio.model.entities.PessoaFisica;
import br.com.lira.portifolio.model.enums.TipoFinanciamento;
import br.com.lira.portifolio.model.exception.ExceptionError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;


public class PessoaTest {

    SimpleDateFormat dataSimples = new SimpleDateFormat("dd/MM/yyyy");

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
