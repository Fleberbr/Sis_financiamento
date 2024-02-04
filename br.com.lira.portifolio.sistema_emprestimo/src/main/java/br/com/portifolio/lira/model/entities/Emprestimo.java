package br.com.portifolio.lira.model.entities;

import br.com.portifolio.lira.model.enums.TipoFinanciamento;
import br.com.portifolio.lira.model.exception.ExceptionError;

public class Emprestimo {

    private int id;
    private double valorEmprestimo;
    private int quantidadeMeses;
    private int quantidadeParcelasPagas;
    TipoFinanciamento tipoFinanciamento;
    Pessoa pessoa;


    public Emprestimo(int id, double valorEmprestimo, int quantidadeMeses,TipoFinanciamento tipoFinanciamento, Pessoa pessoa) {
        this.id = id;
        this.valorEmprestimo = valorEmprestimo;
        this.quantidadeMeses = quantidadeMeses;
        this.tipoFinanciamento = tipoFinanciamento;
        this.pessoa = pessoa;
    }

    public Emprestimo(int id, double valorEmprestimo, int quantidadeMeses,int quantidadeParcelasPagas, TipoFinanciamento tipoFinanciamento, Pessoa pessoa) {
        this.id = id;
        this.valorEmprestimo = valorEmprestimo;
        this.quantidadeMeses = quantidadeMeses;
        this.quantidadeParcelasPagas = quantidadeParcelasPagas;
        this.tipoFinanciamento = tipoFinanciamento;
        this.pessoa = pessoa;
    }

    public int getId() {
        return id;
    }
    public double getValorEmprestimo() {
        return valorEmprestimo;
    }
    public int getQuantidadeMeses() {
        return quantidadeMeses;
    }
    public int getQuantidadeParcelasPagas() {
        return quantidadeParcelasPagas;
    }
    public TipoFinanciamento getTipoFinanciamento() {
        return tipoFinanciamento;
    }
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void realizarPagamento(int quantidadeParcelasPagas) throws ExceptionError {
        if (consultarContratoQuitado())
            throw new ExceptionError("Pagamento não permitido, contrato está quitado");

        if (quantidadeParcelasPagas < 1) {
            throw new ExceptionError("Pagamento não permitido, quantidade de parcelas menor que 1.");
        }

        int parcelaEmAberto = (quantidadeMeses - this.quantidadeParcelasPagas);
        if (quantidadeParcelasPagas > parcelaEmAberto) {
            throw new ExceptionError("Pagamento não permitido, Não é possível pagar mais parcelas do que o necessário.");
        }
        this.quantidadeParcelasPagas = quantidadeParcelasPagas;
        System.out.println("Pagamento efetuado");
    }
    public boolean consultarContratoQuitado(){
        return (quantidadeMeses == quantidadeParcelasPagas);
    }

    public double calcularValorParcelaEmprestimo(){
        return (valorEmprestimo + valorEmprestimo * getPessoa().percentualImposto()/100) / quantidadeMeses;
    }

    public double calcularValorTotalEmprestimo(){
        return valorEmprestimo + valorEmprestimo * getPessoa().percentualImposto()/100 ;
    }

    @Override
    public String toString() {
        return  "Número emprestimo: " + id +
                  pessoa +
                " Valor solicitado do emprestimo: " + String.format("%.2f",valorEmprestimo) +
                " Percentual de Juros: " + getPessoa().percentualImposto()+
                " Valor do emprestimo com juros: " + String.format("%.2f",calcularValorTotalEmprestimo()) +
                " Valor da parcela: " + String.format("%.2f", calcularValorParcelaEmprestimo()) +
                " Quantidade de parcelas: " + getQuantidadeMeses() +
                " Quantidade de parcelas pagas:" + getQuantidadeParcelasPagas() +
                " Tipo Financiamento:" + getTipoFinanciamento()  ;
    }

    public String formatarDadosEmprestimoInsercaoArquivo() {
        return  getId() + "," +
                getValorEmprestimo() + "," +
                getQuantidadeMeses() + "," +
                getQuantidadeParcelasPagas() + "," +
                getTipoFinanciamento() + "," +
                pessoa.formatarDadosPessoaInsercaoArquivo() ;
    }
}
