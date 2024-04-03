package br.com.portifolio.lira.application;

import br.com.portifolio.lira.model.Dao.Implementacao.EmprestimoArquivoRepository;
import br.com.portifolio.lira.model.entities.*;
import br.com.portifolio.lira.model.enums.TipoFinanciamento;
import br.com.portifolio.lira.model.exception.ExceptionError;
import br.com.portifolio.lira.service.EmprestimoService;
import br.com.portifolio.lira.service.PessoaService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Emprestimo> listaEmprestimo = EmprestimoArquivoRepository.buscarDadosEmprestimo();
        PessoaService pessoaService = new PessoaService();
        EmprestimoService emprestimoService = new EmprestimoService();
        Pessoa pessoa = null;
        Emprestimo emprestimo;
        int opcao;

        do {
            System.out.println("\n************* MENU *************");
            System.out.println("0 - Cadastrar um novo cliente");
            System.out.println("1 - Cadastrar um emprestimo");
            System.out.println("2 - realizar pagamento das parcelas");
            System.out.println("3 - Consultar situação do emprestimo");
            System.out.println("4 - Calcular valor da mensalidade");
            System.out.println("5 - Calcular valor total do financiamento");
            System.out.println("6 - Imprimir todos emprestimos");
            System.out.println("7 - Imprimir dados do emprestimo de menor valor");
            System.out.println("8 - imprimir dados do emprestimo de maior valor");
            System.out.println("9 - imprimir valor médio dos emprestimos");
            System.out.println("10- Imprimir valor total dos emprestimos");
            System.out.println("11- Criar arquivo Emprestimo");
            System.out.println("99- Sair");

            System.out.print("Informe uma das opções acima: ");
            opcao = scanner.nextInt();
            switch (opcao) {
                case (0) -> {//Cadastrar um novo cliente
                    pessoa = instanciarPessoa(scanner);
                    pessoaService.cadastrarPessoa(pessoa);
                }//
                case (1) -> {//Cadastrar um financiamento.

                }
                case (2) -> { //Realizar pagamento de parcela
                    System.out.println("===========Pagamento de parcelas===========");
                    System.out.print("Informe o número do financiamento que deseja efetuar pagamento: ");
                    int idEmprestimo = scanner.nextInt();
                    emprestimo = emprestimoService.buscarEmprestimo(idEmprestimo);

                    if (emprestimo != null) {
                        try {
                            System.out.print("Informe a quantidade de parcelas para pagamento: ");
                            int quantidadeParcelasPagas = scanner.nextInt();
                            emprestimo.realizarPagamento(quantidadeParcelasPagas);
                            emprestimoService.atualizarEmprestimo(emprestimo);

                            //EmprestimoArquivoRepository.AtualizarRegistroArquivoEmprestimo(listaEmprestimo);
                        } catch (ExceptionError e) {
                            System.err.print(e + "\n\n");
                        }
                    } else {
                        System.err.print("Emprestimo não encontrado\n\n");
                    }
                }
                case (3) -> consultarSituacaoEmprestimo(scanner, listaEmprestimo);
                case (4) -> consultarMensalidadeContrato(scanner, listaEmprestimo);
                case (5) -> consultarValorTotalContrato(scanner, listaEmprestimo);
                case (6) -> listarEmprestimos(listaEmprestimo);
                case (7) -> imprimirValorMenorEmprestimos(listaEmprestimo);
                case (8) -> imprimirValorMaiorEmprestimos(listaEmprestimo);
                case (9) -> imprimirValorMédioEmprestimos(listaEmprestimo);
                case (10) -> imprimirValorTotalEmprestimos(listaEmprestimo);
                case (11) -> EmprestimoArquivoRepository.criarArquivoEmprestimo();
            }
        } while (opcao != 99);
        System.out.println("Fim de programa!");
    }

    private static Pessoa instanciarPessoa(Scanner scanner) {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        char tipoPessoaInformado;
        try {
            System.out.println("===========Cadastro de cliente===========");
            System.out.print("Informe o nome: ");
            scanner.nextLine();
            String nome = scanner.nextLine();
            System.out.print("Informe o telefone: ");
            String telefone = scanner.next();
            do {
                System.out.print("F - Pessoa fisica \n" +
                        "A - Pessoa fisica aposentado \n" +
                        "J - Pessoa juridica \n" +
                        "Informe o tipo da pessoa:");
                tipoPessoaInformado = scanner.next().toUpperCase().charAt(0);
            } while (!(tipoPessoaInformado == 'A') && !(tipoPessoaInformado == 'F') && !(tipoPessoaInformado == 'J'));

            if (tipoPessoaInformado == 'F') {
                System.out.print("Informe o Cpf: ");
                String id = scanner.next();
                System.out.print("Informe o título de Eleitor: ");
                String tituloEleitor = scanner.next();
                return new PessoaFisica(nome, telefone, id, tituloEleitor);

            } else if (tipoPessoaInformado == 'A') {
                System.out.print("Informe o Cpf: ");
                String id = scanner.next();
                System.out.print("Informe o título de Eleitor: ");
                String tituloEleitor = scanner.next();
                try {
                    System.out.print("Informe a data da aposentadoria (dd/MM/yyyy): ");
                    String dataAposentadoriaDigitada = scanner.next();
                    return new PessoaFisicaAposentada(nome, telefone, id, tituloEleitor, formato.parse(dataAposentadoriaDigitada));
                } catch (Exception e) {
                    System.err.print("Data invalida da aposentaria inválida");
                }
            } else {
                System.out.print("Informe o Cnpj: ");
                String id = scanner.next();
                System.out.print("Informe a inscrição municipal: ");
                String inscricaoMunicipal = scanner.next();
                return new PessoaJuridica(nome, telefone, id, inscricaoMunicipal);
            }
            return null;

        } catch (Exception e) {
            System.err.println("Dado inválido, preencha novamente os dados do cliente:");
            return null;
        }
    }
    private static Emprestimo instanciarEmprestimo(Scanner scanner, Pessoa pessoa) {
        try {
            TipoFinanciamento tipoFinanciamento;

            System.out.print("Informe o valor do emprestimo: ");
            double valorEmprestimo = scanner.nextDouble();
            System.out.print("Informe a quantidade de meses do emprestimo: ");
            int quantidadeMeses = scanner.nextInt();
            System.out.print("Informe o tipo financiamento PESSOAL/ROTATIVO/CONSIGNADO: ");
            tipoFinanciamento = TipoFinanciamento.valueOf(scanner.next().toUpperCase());

            return new Emprestimo(criarIdEmprestimo(), valorEmprestimo, quantidadeMeses, tipoFinanciamento, pessoa);

        } catch (Exception e) {
            System.err.println("Atenção, Entre com dados válidos no emprestimo: ");
            return null;
        }
    }

    public static int criarIdEmprestimo() {
        ArrayList<Emprestimo> listaEmprestimo = EmprestimoArquivoRepository.buscarDadosEmprestimo();
        return (listaEmprestimo.size() == 0) ? 1 : listaEmprestimo.size() + 1;
    }

    public static Emprestimo buscarEmprestimoPorId(ArrayList<Emprestimo> listaEmprestimo, int idEmprestimo) {
        for (Emprestimo emprestimo : listaEmprestimo) {
            if (emprestimo.getId() == idEmprestimo)
                return emprestimo;
        }
        return null;
    }

    private static void realizarPagamentoParcelas(Scanner scanner, ArrayList<Emprestimo> listaEmprestimo) {
        Emprestimo emprestimo;
        System.out.print("Informe o número do financiamento que deseja efetuar pagamento: ");
        int idEmprestimo = scanner.nextInt();
        emprestimo = buscarEmprestimoPorId(listaEmprestimo, idEmprestimo);
        if (emprestimo != null) {
            try {
                int quantidadeParcelasPagas;
                System.out.print("Informe a quantidade de parcelas para pagamento: ");
                quantidadeParcelasPagas = scanner.nextInt();
                emprestimo.realizarPagamento(quantidadeParcelasPagas);
                EmprestimoArquivoRepository.AtualizarRegistroArquivoEmprestimo(listaEmprestimo);
            } catch (ExceptionError e) {
                System.err.print(e + "\n\n");
            }
        } else {
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }

    private static void consultarMensalidadeContrato(Scanner scanner, ArrayList<Emprestimo> listaEmprestimo) {
        Emprestimo emprestimo;
        System.out.print("Informe o número do emprestimo para consultar a mensalidade: ");
        int idEmprestimo = scanner.nextInt();

        emprestimo = buscarEmprestimoPorId(listaEmprestimo, idEmprestimo);
        if (emprestimo != null) {

            System.out.println(emprestimo.getValorTotalEmprestimo());
        } else {
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }

    private static void consultarValorTotalContrato(Scanner scanner, ArrayList<Emprestimo> listaEmprestimo) {
        Emprestimo emprestimo;
        System.out.print("Informe o número do emprestimo para consultar a mensalidade: ");
        int idEmprestimo = scanner.nextInt();
        emprestimo = buscarEmprestimoPorId(listaEmprestimo, idEmprestimo);
        if (emprestimo != null) {
            System.out.println(emprestimo.getValorTotalEmprestimo());
        } else {
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }

    private static void consultarSituacaoEmprestimo(Scanner scanner, ArrayList<Emprestimo> listaEmprestimo) {
        Emprestimo emprestimo;
        System.out.print("Informe o número do financiamento que consultar a situacao: ");
        int idEmprestimo = scanner.nextInt();
        emprestimo = buscarEmprestimoPorId(listaEmprestimo, idEmprestimo);
        if (emprestimo != null) {
            System.out.println(emprestimo.consultarContratoQuitado() ? "CONTRATO QUITADO" : "CONTRATO EM ABERTO");
        } else {
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }

    public static void listarEmprestimos(ArrayList<Emprestimo> listaEmprestimo) {
        listaEmprestimo.forEach(System.out::println);
    }

    public static void imprimirValorMédioEmprestimos(ArrayList<Emprestimo> listaEmprestimo) {
        double valorMedioEmprestimos = listaEmprestimo
                .stream()
                .mapToDouble(Emprestimo::getValorEmprestimo)
                .average().orElse(0);
        System.out.println("Valor médio dos emprestimos: " + String.format("%.2f", valorMedioEmprestimos));
    }

    public static void imprimirValorMenorEmprestimos(ArrayList<Emprestimo> listaEmprestimo) {
        double valorMenorEmprestimos = listaEmprestimo
                .stream()
                .mapToDouble(Emprestimo::getValorEmprestimo)
                .min().orElse(0);
        System.out.println("Valor Menor dos emprestimos: " + String.format("%.2f", valorMenorEmprestimos));
    }

    public static void imprimirValorMaiorEmprestimos(ArrayList<Emprestimo> listaEmprestimo) {
        double valorMaiorEmprestimos = listaEmprestimo
                .stream()
                .mapToDouble(Emprestimo::getValorEmprestimo)
                .max().orElse(0);
        System.out.println("Valor Maior dos emprestimos: " + String.format("%.2f", valorMaiorEmprestimos));
    }

    public static void imprimirValorTotalEmprestimos(ArrayList<Emprestimo> listaEmprestimo) {
        double valorTotalEmprestimos = listaEmprestimo
                .stream()
                .mapToDouble(Emprestimo::getValorEmprestimo)
                .sum();
        System.out.println("Valor total dos emprestimos: " + String.format("%.2f", valorTotalEmprestimos));
    }
}