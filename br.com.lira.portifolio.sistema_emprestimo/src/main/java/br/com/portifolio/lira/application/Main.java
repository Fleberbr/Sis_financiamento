package br.com.portifolio.lira.application;

import br.com.portifolio.lira.model.entities.*;
import br.com.portifolio.lira.model.enums.TipoFinanciamento;
import br.com.portifolio.lira.model.exception.ExceptionError;
import br.com.portifolio.lira.repository.emprestimoArquivoRepository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Emprestimo> listaEmprestimo = emprestimoArquivoRepository.buscarDadosEmprestimo();
        int opcao ;
        Pessoa pessoa = null;
        Emprestimo emprestimo;

        /*try {
            emprestimoRepository emprestimoRepository = new emprestimoRepository("C:\\Users\\elton\\OneDrive\\Documentos\\Projetos programação\\ws-eclipse\\sisEmprestimo\\br.com.lira.portifolio.sistema_emprestimo");
        }catch (IOException e){
            System.err.println("Erro ao setar o caminho do path");
        }
        System.out.println("Data nascimento: ");
        Date dataNascimento = dataSimples.parse(scanner.next());
        String dataNascimento = dataSimples.format("11/06/2024");

        pessoa = new PessoaFisica("Elton","11971","1","111");
        emprestimo = new Emprestimo(Integer.toString(criarIdEmprestimo(listaEmprestimo)),10000.00,10, TipoFinanciamento.CONSIGNADO,pessoa);
        emprestimoRepository.inserirDadosArquivoEmprestimo(emprestimo);

        pessoa = new PessoaJuridica("Elton","11971","2","111");
        emprestimo = new Emprestimo(Integer.toString(criarIdEmprestimo(listaEmprestimo)),20000.00,10, TipoFinanciamento.PESSOAL,pessoa);
        emprestimoRepository.inserirDadosArquivoEmprestimo(emprestimo);

        pessoa = new PessoaFisicaAposentada("Elton","11971","3","111",formato.parse("10/06/2024"));
        emprestimo = new Emprestimo(Integer.toString(criarIdEmprestimo(listaEmprestimo)),30000.00,10, TipoFinanciamento.ROTATIVO,pessoa);
        emprestimoRepository.inserirDadosArquivoEmprestimo(emprestimo);*/
        do {
            System.out.println("\n************* MENU *************");
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
            System.out.print("Informe uma das opções acima: ");
            opcao = scanner.nextInt();
            switch (opcao) {
                case (1) -> /*Inserir um novo emprestimo*/
                {
                   pessoa = cadastrarPessoa(scanner, pessoa);
                   emprestimo = cadastrarEmprestimo(scanner,pessoa);
                   emprestimoArquivoRepository.inserirNovoRegistroArquivoEmprestimo(emprestimo);
                   //atualiza a lista de emprestimos, buscando do arquivo
                   listaEmprestimo = emprestimoArquivoRepository.buscarDadosEmprestimo();
                   //listaEmprestimo.add(emprestimo);
                }
                case (2) -> realizarPagamentoParcelas(scanner, listaEmprestimo);
                case (3) -> consultarSituacaoEmprestimo(scanner, listaEmprestimo);
                case (4) -> consultarMensalidadeContrato(scanner, listaEmprestimo);
                case (5) -> consultarValorTotalContrato(scanner, listaEmprestimo);
                case (6) -> listarEmprestimos(listaEmprestimo);
                case (7) -> imprimirValorMenorEmprestimos(listaEmprestimo);
                case (8) -> imprimirValorMaiorEmprestimos(listaEmprestimo);
                case (9) -> imprimirValorMédioEmprestimos(listaEmprestimo);
                case (10)-> imprimirValorTotalEmprestimos(listaEmprestimo);
                case (11)-> emprestimoArquivoRepository.criarArquivoEmprestimo();
                }
        }while (opcao != 0);
        System.out.println("Fim de programa!");
    }

    private static Pessoa cadastrarPessoa(Scanner scanner, Pessoa pessoa) {

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        char tipoPessoaInformado ;
        try {
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
                pessoa = new PessoaFisica(nome, telefone, id, tituloEleitor);

            } else if (tipoPessoaInformado == 'A') {
                System.out.print("Informe o Cpf: ");
                String id = scanner.next();
                System.out.print("Informe o título de Eleitor: ");
                String tituloEleitor = scanner.next();
                try {
                    System.out.print("Informe a data da aposentadoria (dd/MM/yyyy): ");
                    String dataAposentadoriaDigitada = scanner.next();
                    pessoa = new PessoaFisicaAposentada(nome, telefone, id, tituloEleitor, formato.parse(dataAposentadoriaDigitada));
                } catch (Exception e) {
                    System.err.print("Data invalida da aposentaria inválida");
                }
            } else {
                System.out.print("Informe o Cnpj: ");
                String id = scanner.next();
                System.out.print("Informe a inscrição municipal: ");
                String inscricaoMunicipal = scanner.next();
                pessoa = new PessoaJuridica(nome, telefone, id, inscricaoMunicipal);
            }
            return pessoa;
        }catch (Exception e){
            System.err.println("Dado inválido, preencha novamente os dados do cliente:");
            return null;
        }
    }
    private static Emprestimo cadastrarEmprestimo(Scanner scanner, Pessoa pessoa) {
        try {
            TipoFinanciamento tipoFinanciamento;
            Emprestimo emprestimo;
            System.out.print("Informe o valor do emprestimo: ");
            Double valorEmprestimo = scanner.nextDouble();
            System.out.print("Informe a quantidade de meses do emprestimo: ");
            int quantidadeMeses = scanner.nextInt();
            System.out.print("Informe o tipo financiamento PESSOAL/ROTATIVO/CONSIGNADO: ");
            tipoFinanciamento = TipoFinanciamento.valueOf(scanner.next().toUpperCase());
            emprestimo = new Emprestimo(criarIdEmprestimo(), valorEmprestimo, quantidadeMeses, tipoFinanciamento, pessoa);

            return emprestimo;
        }catch (Exception e){
            System.err.println("Atenção, Entre com dados válidos no emprestimo: ");
            return null;
        }
    }

    public static boolean validarCampo(String campoInformado) {
        if (campoInformado.isBlank()) {
            System.err.print("Campos inválido: O campo está em branco \n");
            return false;
        }
        return true;
    }

    public static int criarIdEmprestimo(){
        ArrayList<Emprestimo> listaEmprestimo = emprestimoArquivoRepository.buscarDadosEmprestimo();
        return (listaEmprestimo.size() == 0) ? 1 : listaEmprestimo.size() + 1;
    }
    public static Emprestimo buscarEmprestimoPorId(ArrayList<Emprestimo> listaEmprestimo, int idEmprestimo){
        for (Emprestimo emprestimo : listaEmprestimo) {
            if (emprestimo.getId() == idEmprestimo)
                return emprestimo;
        }
        return null;
    }

    private static void realizarPagamentoParcelas(Scanner scanner, ArrayList<Emprestimo> listaEmprestimo) {
        Emprestimo emprestimo;
        System.out.print("Informe o número do financiamento que deseja efetuar pagamento: ");
        int idEmprestimo =  scanner.nextInt();
        emprestimo = buscarEmprestimoPorId(listaEmprestimo, idEmprestimo);
        if (emprestimo != null){
            try{
                int quantidadeParcelasPagas;
                System.out.print("Informe a quantidade de parcelas para pagamento: ");
                quantidadeParcelasPagas = scanner.nextInt();
                emprestimo.realizarPagamento(quantidadeParcelasPagas);
                emprestimoArquivoRepository.AtualizarRegistroArquivoEmprestimo(listaEmprestimo);
            }catch(ExceptionError e){
                System.err.print(e+"\n\n");
            }
        }
        else{
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }
    private static void consultarMensalidadeContrato(Scanner scanner, ArrayList<Emprestimo> listaEmprestimo) {
        Emprestimo emprestimo;
        System.out.print("Informe o número do emprestimo para consultar a mensalidade: ");
        int idEmprestimo =  scanner.nextInt();

        emprestimo = buscarEmprestimoPorId(listaEmprestimo, idEmprestimo);
        if (emprestimo != null){
            System.out.println(emprestimo.calcularValorParcelaEmprestimo());
        }
        else{
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }
    private static void consultarValorTotalContrato(Scanner scanner, ArrayList<Emprestimo> listaEmprestimo) {
        Emprestimo emprestimo;
        System.out.print("Informe o número do emprestimo para consultar a mensalidade: ");
        int idEmprestimo =  scanner.nextInt();
        emprestimo = buscarEmprestimoPorId(listaEmprestimo, idEmprestimo);
        if (emprestimo != null){
            System.out.println(emprestimo.calcularValorTotalEmprestimo());
        }
        else{
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }
    private static void consultarSituacaoEmprestimo(Scanner scanner, ArrayList<Emprestimo> listaEmprestimo) {
        Emprestimo emprestimo;
        System.out.print("Informe o número do financiamento que consultar a situacao: ");
        int idEmprestimo =  scanner.nextInt();
        emprestimo = buscarEmprestimoPorId(listaEmprestimo, idEmprestimo);
        if (emprestimo != null){
            System.out.println(emprestimo.consultarContratoQuitado()? "CONTRATO QUITADO":"CONTRATO EM ABERTO");
        }
        else{
            System.err.print("Emprestimo não encontrado\n\n");
        }
    }
    public static void listarEmprestimos(ArrayList<Emprestimo> listaEmprestimo){
        listaEmprestimo.forEach(System.out::println);
    }
    public static void imprimirValorMédioEmprestimos(ArrayList<Emprestimo> listaEmprestimo){
        double valorMedioEmprestimos = listaEmprestimo
               .stream()
               .mapToDouble(Emprestimo::getValorEmprestimo)
               .average().orElse(0);
        System.out.println("Valor médio dos emprestimos: "+ String.format("%.2f",valorMedioEmprestimos));
    }
    public static void imprimirValorMenorEmprestimos(ArrayList<Emprestimo> listaEmprestimo){
        double valorMenorEmprestimos = listaEmprestimo
               .stream()
               .mapToDouble(Emprestimo::getValorEmprestimo)
               .min().orElse(0);
        System.out.println("Valor Menor dos emprestimos: "+ String.format("%.2f",valorMenorEmprestimos));
    }
    public static void imprimirValorMaiorEmprestimos(ArrayList<Emprestimo> listaEmprestimo){
        double valorMaiorEmprestimos = listaEmprestimo
               .stream()
               .mapToDouble(Emprestimo::getValorEmprestimo)
               .max().orElse(0);
        System.out.println("Valor Maior dos emprestimos: "+ String.format("%.2f",valorMaiorEmprestimos));
    }
    public static void imprimirValorTotalEmprestimos(ArrayList<Emprestimo> listaEmprestimo){
        double valorTotalEmprestimos = listaEmprestimo
                .stream()
                .mapToDouble(Emprestimo::getValorEmprestimo)
                .sum();
        System.out.println("Valor total dos emprestimos: "+ String.format("%.2f",valorTotalEmprestimos));
    }
}