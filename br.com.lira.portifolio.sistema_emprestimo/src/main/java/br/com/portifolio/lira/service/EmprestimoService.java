package br.com.portifolio.lira.service;

import br.com.portifolio.lira.model.Dao.DaoFactory;
import br.com.portifolio.lira.model.Dao.EmprestimoDao;
import br.com.portifolio.lira.model.entities.Emprestimo;
import br.com.portifolio.lira.model.exception.ExceptionError;



public class EmprestimoService {

    Emprestimo emprestimo;
    static EmprestimoDao emprestimoDao = DaoFactory.createEmprestimoDAO();

    public void cadastrarEmprestimo(Emprestimo emprestimo) {
        emprestimoDao.insert(emprestimo);
    }

    public Emprestimo buscarEmprestimo(Integer id) {
        return emprestimoDao.findById(id);
    }

    public void atualizarEmprestimo(Emprestimo emprestimo) {
        emprestimoDao.update(emprestimo);
    }

    public void realizarPagamentoParcelaEmprestimo(int id, int quantidadeparcelasPagas) throws ExceptionError {
        try {
            emprestimo = buscarEmprestimo(id);
            if (emprestimo != null) {
                emprestimo.realizarPagamento(quantidadeparcelasPagas);
                atualizarEmprestimo(emprestimo);
                System.out.println("Parcelas do contrato pagas!");
            } else {
                System.err.print("Emprestimo não encontrado\n\n");
            }
        } catch (ExceptionError e) {
            throw new ExceptionError(e.getMessage());
        }
    }

    public void consultarSituacaoEmprestimo(int id) {
        emprestimo = buscarEmprestimo(id);
        if (emprestimo != null) {
            System.out.println("************* CONSULTA SITUAÇÃO DO CONTRATO *************");
            System.out.println(emprestimo.consultarContratoQuitado() ? "CONTRATO QUITADO" : "CONTRATO EM ABERTO");
            System.out.println("************* CONSULTA SITUAÇÃO DO CONTRATO *************");
        } else {
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }

    public void consultarMensalidadeContrato(int id) {
        emprestimo = buscarEmprestimo(id);
        if (emprestimo != null) {
            System.out.println("************* C0NSULTA VALOR PARCELA DO EMPRESTIMO *************");
            System.out.println(emprestimo.getValorParcela());
            System.out.println("************* C0NSULTA VALOR PARCELA DO EMPRESTIMO *************");
        } else {
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }

    public void consultarValorTotalContrato(int id) {
        emprestimo = buscarEmprestimo(id);
        if (emprestimo != null) {
            System.out.println("************* C0NSULTA MENSALIDADE EMPRESTIMO *************");
            System.out.println(emprestimo.getValorParcela());
            System.out.println("************* C0NSULTA MENSALIDADE EMPRESTIMO *************");
        } else {
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }

    public static void imprimirEmprestimos() {
        System.out.println("************* LISTA DE EMPRESTIMO *************");
        emprestimoDao.findAll().forEach(System.out::println);
        System.out.println("************* LISTA DE EMPRESTIMO *************");
    }

    public static void imprimirValorMedioEmprestimos() {
        double valorMedioEmprestimos = emprestimoDao.findAll()
                .stream()
                .mapToDouble(Emprestimo::getValorEmprestimo)
                .average().orElse(0);
        System.out.println("************* INDICADORES EMPRESTIMO *************");
        System.out.println("Valor médio dos emprestimos: " + String.format("%.2f", valorMedioEmprestimos));
        System.out.println("************* INDICADORES EMPRESTIMO *************");
    }

    public static void imprimirValorMenorEmprestimos() {
        double valorMenorEmprestimos = emprestimoDao.findAll()
                .stream()
                .mapToDouble(Emprestimo::getValorEmprestimo)
                .min().orElse(0);
        System.out.println("************* INDICADORES EMPRESTIMO *************");
        System.out.println("Valor Menor dos emprestimos: " + String.format("%.2f", valorMenorEmprestimos));
        System.out.println("************* INDICADORES EMPRESTIMO *************");
    }

    public static void imprimirValorMaiorEmprestimos() {
        double valorMaiorEmprestimos = emprestimoDao.findAll()
                .stream()
                .mapToDouble(Emprestimo::getValorEmprestimo)
                .max().orElse(0);
        System.out.println("************* INDICADORES EMPRESTIMO *************");
        System.out.println("Valor Maior dos emprestimos: " + String.format("%.2f", valorMaiorEmprestimos));
        System.out.println("************* INDICADORES EMPRESTIMO *************");
    }

    public static void imprimirValorTotalEmprestimos() {
        double valorTotalEmprestimos = emprestimoDao.findAll()
                .stream()
                .mapToDouble(Emprestimo::getValorEmprestimo)
                .sum();
        System.out.println("************* INDICADORES EMPRESTIMO *************");
        System.out.println("Valor total dos emprestimos: " + String.format("%.2f", valorTotalEmprestimos));
        System.out.println("************* INDICADORES EMPRESTIMO *************");
    }
}
