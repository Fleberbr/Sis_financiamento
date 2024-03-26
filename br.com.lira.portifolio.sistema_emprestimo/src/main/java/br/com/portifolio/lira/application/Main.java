package br.com.portifolio.lira.application;

import br.com.portifolio.lira.model.entities.*;
import br.com.portifolio.lira.model.enums.TipoFinanciamento;
import br.com.portifolio.lira.model.exception.ExceptionError;
import br.com.portifolio.lira.model.Dao.Implementacao.EmprestimoArquivoRepository;
import br.com.portifolio.lira.service.EmprestimoService;
import br.com.portifolio.lira.service.PessoaService;

import java.text.SimpleDateFormat;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PessoaService pessoaService = new PessoaService();
        EmprestimoService emprestimoService = new EmprestimoService();
        Pessoa pessoa;
        Emprestimo emprestimo;
        int opcao;

        do {
            System.out.println("\n************* MENU *************");
            System.out.println("1 - Acessar menu de cliente");
            System.out.println("2 - Acessar menu de emprestimos");
            System.out.println("0 - Sair");
            System.out.print("Digite o número para acessar uma das opções acima:");
            opcao = scanner.nextInt();
            switch (opcao) {
                case (0) -> System.out.println("Fim de programa!");
                case (1) -> {//Menu de cliente
                    while (opcao != 99) {
                        System.out.println("\n************* MENU CADASTRO DE CLIENTE*************");
                        System.out.println("1 - Cadastrar um novo cliente");
                        System.out.println("2 - Pesquisar cliente");
                        System.out.println("3 - Listar clientes cadastrados");
                        System.out.println("4 - Em construção");
                        System.out.println("99 - Retornar ao menu");
                        System.out.print("Digite o número para acessar uma das opções acima:");
                        opcao = scanner.nextInt();
                        switch (opcao) {
                            case (99) -> {
                            } //Voltar o menu.
                            case (1) -> {//Cadastrar um novo cliente
                                pessoa = instanciarPessoa(scanner);
                                pessoaService.cadastrarPessoa(pessoa);
                            }
                            case (2) -> {
                                System.out.print("Informe o cpf/cnpj do cliente: ");
                                String id = scanner.next();
                                pessoa = PessoaService.buscarPessoa(id);
                                System.out.println(pessoa);
                            }
                            case (3) -> PessoaService.imprimirListaPessoa();
                            case (4) -> {//Em construção
                            }
                        }
                    }
                }
                case (2) -> {//Menu de emprestimo
                    do {
                        System.out.println("\n************* MENU CADASTRO DE EMPRESTIMO *************");
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
                        System.out.println("99- Voltar ao menu principal");

                        System.out.print("Informe uma das opções acima: ");
                        opcao = scanner.nextInt();
                        switch (opcao) {
                            case (1) -> {//Cadastrar um financiamento.
                                System.out.print("Informe o cpf/cnpj do cliente: ");
                                String id = scanner.next();
                                pessoa = pessoaService.buscarPessoa(id);
                                if (pessoa != null) {
                                    emprestimo = instanciarEmprestimo(scanner, pessoa);
                                    emprestimoService.cadastrarEmprestimo(emprestimo);
                                } else {
                                    System.err.println("Não foi possível localizar o cliente, por favor efetue o cadastro do cliente antes de efetuar o emprestimo");
                                }
                                //EmprestimoArquivoRepository.inserirNovoRegistroArquivoEmprestimo(emprestimo);
                                //atualiza a lista de emprestimos, buscando do arquivo
                                //listaEmprestimo = EmprestimoArquivoRepository.buscarDadosEmprestimo();
                                //listaEmprestimo.add(emprestimo);
                            }
                            case (2) -> { //Realizar pagamento de parcela
                                try {
                                    System.out.println("===========Pagamento de parcelas===========");
                                    System.out.print("Informe o número do financiamento que deseja efetuar pagamento: ");
                                    int id = scanner.nextInt();
                                    System.out.print("Informe a quantidade de parcelas para pagamento: ");
                                    int quantidadeParcelasPagas = scanner.nextInt();
                                    emprestimoService.realizarPagamentoParcelaEmprestimo(id, quantidadeParcelasPagas);

                                } catch (ExceptionError e) {
                                    System.err.print(e + "\n\n");
                                }
                            }
                            case (3) -> {
                                System.out.print("Informe o número do financiamento que consultar a situacao: ");
                                int id = scanner.nextInt();
                                emprestimoService.consultarSituacaoEmprestimo(id);
                            }
                            case (4) -> {
                                System.out.print("Informe o número do emprestimo para consultar a mensalidade: ");
                                int id = scanner.nextInt();
                                emprestimoService.consultarMensalidadeContrato(id);
                            }
                            case (5) -> {
                                System.out.print("Informe o número do emprestimo para consultar o valor total do emprestimo: ");
                                int id = scanner.nextInt();
                                emprestimoService.consultarValorTotalContrato(id);
                            }
                            case (6) -> EmprestimoService.imprimirEmprestimos();
                            case (7) -> EmprestimoService.imprimirValorMenorEmprestimos();
                            case (8) -> EmprestimoService.imprimirValorMaiorEmprestimos();
                            case (9) -> EmprestimoService.imprimirValorMedioEmprestimos();
                            case (10) -> EmprestimoService.imprimirValorTotalEmprestimos();

                            case (11) -> EmprestimoArquivoRepository.criarArquivoEmprestimo();
                        }
                    } while (opcao != 99);
                }
            }
        } while (opcao != 0);
    }

    private static Pessoa instanciarPessoa(Scanner scanner) {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        char tipoPessoaInformado;
        try {
            System.out.println("\n************* MENU CADASTRO DE CLIENTE*************");

            do {
                System.out.println("Informe o tipo da pessoa a ser cadastrado.");
                System.out.print("""
                        F -> Pessoa fisica\s
                        A -> Pessoa fisica aposentado\s
                        J -> Pessoa juridica\s
                        Opção escolhida:\s""");
                tipoPessoaInformado = scanner.next().toUpperCase().charAt(0);
            } while (!(tipoPessoaInformado == 'A') && !(tipoPessoaInformado == 'F') && !(tipoPessoaInformado == 'J'));

            System.out.print("Informe o CPF/CNPJ do cliente: ");
            String id = scanner.next();

            System.out.print("Informe o nome do cliente: ");
            scanner.nextLine();
            String nome = scanner.nextLine();

            System.out.print("Informe o telefone: ");
            String telefone = scanner.next();

            //PF ou PF aposentada
            if (tipoPessoaInformado == 'F' || tipoPessoaInformado == 'A') {

                System.out.print("Informe o título de Eleitor: ");
                String tituloEleitor = scanner.next();

                if (tipoPessoaInformado == 'F') {
                    return new PessoaFisica(nome, telefone, id, tituloEleitor);
                } else {
                    try {
                        System.out.print("Informe a data da aposentadoria (dd/MM/yyyy): ");
                        String dataAposentadoria = scanner.next();
                        return new PessoaFisicaAposentada(nome, telefone, id, tituloEleitor, formato.parse(dataAposentadoria));
                    } catch (Exception e) {
                        System.err.print("Data invalida da aposentaria inválida");
                    }
                }
            }
            //Pessoa juridica
            else {
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
            System.out.print("Informe o valor do emprestimo: ");
            double valorEmprestimo = scanner.nextDouble();
            System.out.print("Informe a quantidade de meses do emprestimo: ");
            int quantidadeMeses = scanner.nextInt();
            System.out.print("Informe o tipo financiamento PESSOAL/ROTATIVO/CONSIGNADO: ");
            TipoFinanciamento tipoFinanciamento = TipoFinanciamento.valueOf(scanner.next().toUpperCase());
            return new Emprestimo(null, valorEmprestimo, quantidadeMeses, tipoFinanciamento, pessoa);

        } catch (Exception e) {
            System.err.println("Atenção, Entre com dados válidos no emprestimo.");
            return null;
        }
    }
}