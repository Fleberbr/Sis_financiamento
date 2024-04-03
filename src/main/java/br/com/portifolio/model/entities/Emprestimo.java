package br.com.portifolio.model.entities;

import br.com.portifolio.model.enums.TipoFinanciamento;
import br.com.portifolio.model.exception.ExceptionError;

public class Emprestimo {

    private Integer id;
    private double valorEmprestimo;
    private int quantidadeMeses;
    private int quantidadeParcelasPagas;
    private double valorJuros;
    private double valorTotalEmprestimo;
    private double valorParcela;

    TipoFinanciamento tipoFinanciamento;
    Pessoa pessoa;



    public Emprestimo(Integer id, double valorEmprestimo, int quantidadeMeses, TipoFinanciamento tipoFinanciamento, Pessoa pessoa) {
        this.id = id;
        this.valorEmprestimo = valorEmprestimo;
        this.quantidadeMeses = quantidadeMeses;
        this.quantidadeParcelasPagas = 0;
        this.tipoFinanciamento = tipoFinanciamento;
        this.pessoa = pessoa;
        calcularValorJurosEmprestimo();
        calcularValorTotalEmprestimo();
        calcularValorParcelaEmprestimo();
    }

    public Emprestimo(Integer id, double valorEmprestimo, int quantidadeMeses, int quantidadeParcelasPagas, TipoFinanciamento tipoFinanciamento, Pessoa pessoa) {
        this.id = id;
        this.valorEmprestimo = valorEmprestimo;
        this.quantidadeMeses = quantidadeMeses;
        this.quantidadeParcelasPagas = quantidadeParcelasPagas;
        this.tipoFinanciamento = tipoFinanciamento;
        this.pessoa = pessoa;
        calcularValorJurosEmprestimo();
        calcularValorTotalEmprestimo();
        calcularValorParcelaEmprestimo();
    }

    public Emprestimo(Integer id, double valorEmprestimo, int quantidadeMeses,  int quantidadeParcelasPagas, double valorJuros, double valorTotalEmprestimo,  double valorParcela, TipoFinanciamento tipoFinanciamento, Pessoa pessoa) {
        this.id = id;
        this.valorEmprestimo = valorEmprestimo;
        this.quantidadeMeses = quantidadeMeses;
        this.quantidadeParcelasPagas = quantidadeParcelasPagas;
        this.valorJuros = valorJuros;
        this.valorTotalEmprestimo = valorTotalEmprestimo;
        this.valorParcela = valorParcela;
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

    public double getValorTotalEmprestimo() {
        return valorTotalEmprestimo;
    }

    public double getValorJuros() {
        return valorJuros;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public TipoFinanciamento getTipoFinanciamento() {
        return tipoFinanciamento;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public double getValorParcela() {
        return valorParcela;
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
        this.quantidadeParcelasPagas += quantidadeParcelasPagas;
        //System.out.println("Pagamento efetuado");
    }

    public boolean consultarContratoQuitado() {
        return (quantidadeMeses == quantidadeParcelasPagas);
    }

    public void calcularValorParcelaEmprestimo() {
        this.valorParcela =  (valorEmprestimo + valorEmprestimo * getPessoa().percentualImposto() / 100) / quantidadeMeses;
    }

    public void calcularValorJurosEmprestimo() {
        this.valorJuros = valorEmprestimo * getPessoa().percentualImposto() / 100;
    }

    public void calcularValorTotalEmprestimo() {
        this.valorTotalEmprestimo = valorEmprestimo + valorEmprestimo * getPessoa().percentualImposto() / 100;
    }

    @Override
    public String toString() {
        return "Número emprestimo: " + id + " "+
                pessoa +
                " Valor solicitado do emprestimo: " + String.format("%.2f", getValorEmprestimo()) +
                " Percentual de Juros: " + getPessoa().percentualImposto() +
                " Valor do emprestimo com juros: " + String.format("%.2f", getValorEmprestimo()) +
                " Valor da parcela: " + String.format("%.2f", getValorParcela()) +
                " Quantidade de parcelas: " + getQuantidadeMeses() +
                " Quantidade de parcelas pagas:" + getQuantidadeParcelasPagas() +
                " Tipo Financiamento:" + getTipoFinanciamento();
    }

    public String formatarDadosEmprestimoInsercaoArquivo() {
        return getId() + "," +
                getValorEmprestimo() + "," +
                getQuantidadeMeses() + "," +
                getQuantidadeParcelasPagas() + "," +
                getTipoFinanciamento() + "," +
                pessoa.formatarDadosPessoaInsercaoArquivo();
    }
}
