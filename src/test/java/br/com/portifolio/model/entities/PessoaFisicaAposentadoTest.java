package br.com.portifolio.model.entities;


import br.com.portifolio.model.entities.PessoaFisicaAposentada;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PessoaFisicaAposentadoTest {

    SimpleDateFormat dataSimples = new SimpleDateFormat("dd-MM-yyyy");
    PessoaFisicaAposentada pessoa;

    @BeforeEach // executa antes dos testes
    public void configuracaoInicial() throws ParseException {

        pessoa = new PessoaFisicaAposentada("Elton","11971","1","111",dataSimples.parse("01-01-2023"));
    }

    @Test
    public void testeCapturarPercentuaImposto(){
        double percentual = pessoa.percentualImposto();

        Assertions.assertEquals(3.00,percentual);
    }

    @Test
    public void testeToString(){

        Assertions.assertEquals(" Cpf: 1 Nome: Elton Tipo pessoa: 111 Telefone: 11971 Titulo de eleitor: 111 Data aposentadoria: Sun Jan 01 00:00:00 BRT 2023",pessoa.toString() );
    }
}
