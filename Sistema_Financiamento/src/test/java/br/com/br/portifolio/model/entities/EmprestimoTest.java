package br.com.br.portifolio.model.entities;

import br.com.portifolio.lira.model.entities.Emprestimo;
import br.com.portifolio.lira.model.entities.Pessoa;
import br.com.portifolio.lira.model.entities.PessoaFisica;
import br.com.portifolio.lira.model.enums.TipoFinanciamento;
import br.com.portifolio.lira.model.enums.TipoPessoa;
import br.com.portifolio.lira.model.exception.ExceptionError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class EmprestimoTest {

    private Pessoa pessoa;
    private Emprestimo emprestimo;
    ArrayList<Emprestimo> listaEmprestimo = new ArrayList<>();


    public static int criarIdEmprestimo(ArrayList<Emprestimo> listaEmprestimo) {
        return (listaEmprestimo.size() == 0) ? 1 : listaEmprestimo.size() + 1;
    }

    @BeforeEach // executa antes dos testes
    public void configuracaoInicial() {
        this.pessoa = new PessoaFisica("Elton","11971","1","111", TipoPessoa.PESSOA_FISICA);
        this.emprestimo = new Emprestimo(1,10000.00,10,TipoFinanciamento.CONSIGNADO, pessoa);
    }



    @Test //o método aqui, sempre será void e não recebe parametros
    public void testeRealizarPagamento_Erro_ContratoQuitado() throws Exception{
        this.emprestimo.realizarPagamento(10);

        Throwable exception = Assertions.assertThrows(ExceptionError.class, () -> this.emprestimo.realizarPagamento(1));
        Assertions.assertEquals(exception.getMessage(), "Pagamento não permitido, contrato está quitado");
    }

    @Test //o método aqui, sempre será void e não recebe parametros
    public void testeRealizarPagamento_Erro_ParcelaNegativa() {
        Throwable exception = Assertions.assertThrows(ExceptionError.class, () -> this.emprestimo.realizarPagamento(-1));
        Assertions.assertEquals(exception.getMessage(), "Pagamento não permitido, quantidade de parcelas menor que 1.");
    }

    @Test //o método aqui, sempre será void e não recebe parametros
    public void testeRealizarPagamento_Erro_PagarMaisParcelasQueNecessario() {
        Throwable exception = Assertions.assertThrows(ExceptionError.class, () -> this.emprestimo.realizarPagamento(11));
        Assertions.assertEquals(exception.getMessage(), "Pagamento não permitido, Não é possível pagar mais parcelas do que o necessário.");
    }

    @Test //o método aqui, sempre será void e não recebe parametros
    public void testeRealizarPagamento_com_sucesso() {
        Throwable exception = Assertions.assertThrows(ExceptionError.class, () -> this.emprestimo.realizarPagamento(11));
        Assertions.assertEquals(exception.getMessage(), "Pagamento não permitido, Não é possível pagar mais parcelas do que o necessário.");
    }



    @Test //o método aqui, sempre será void e não recebe parametros
    public void testeContratoQuitado() throws ExceptionError {
        this.emprestimo.realizarPagamento(10);

        Assertions.assertTrue(this.emprestimo.consultarContratoQuitado());
    }

    @Test //o método aqui, sempre será void e não recebe parametros
    public void testeContratoEmAberto(){
        Assertions.assertFalse(this.emprestimo.consultarContratoQuitado());
    }

    @Test
    public void testeCalcularValorTotalEmprestimo(){
        Double valorAtual = this.emprestimo.getValorTotalEmprestimo();

        Assertions.assertEquals(11000.00,valorAtual);
    }

    @Test
    public void testeCalcularValorParcelaEmprestimo(){

        Double valorParcelaAtual = this.emprestimo.getValorParcela();
        Assertions.assertEquals(1100.00,valorParcelaAtual);
    }
}
