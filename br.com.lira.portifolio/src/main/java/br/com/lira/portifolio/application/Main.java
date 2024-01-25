package br.com.lira.portifolio;

import model.entities.*;
import model.enums.TipoFinanciamento;
import model.exception.ExceptionError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.Scanner;




public class Main {
    public static void main(String[] args) throws ParseException {

        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dataSimples = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<Emprestimo> listaEmprestimo = new ArrayList<>();
        Emprestimo emprestimo;
        Pessoa pessoa;
        TipoFinanciamento tipoFinanciamento;
        String data;
        int opcao ;

        //System.out.println("Data nascimento: ");
        //Date dataNascimento = dataSimples.parse(scanner.next());
        //String dataNascimento = dataSimples.format("11/06/2024");

        pessoa = new PessoaFisica("Elton","11971","1","111");
        emprestimo = new Emprestimo(Integer.toString(criarIdEmprestimo(listaEmprestimo)),10000.00,10, TipoFinanciamento.CONSIGNADO,pessoa);
        listaEmprestimo.add(emprestimo);

        pessoa = new PessoaJuridica("Elton","11971","2","111");
        emprestimo = new Emprestimo(Integer.toString(criarIdEmprestimo(listaEmprestimo)),20000.00,10, TipoFinanciamento.PESSOAL,pessoa);
        listaEmprestimo.add(emprestimo);

        pessoa = new PessoaFisicaAposentada("Elton","11971","3","111",dataSimples.parse("11/06/2024"));
        emprestimo = new Emprestimo(Integer.toString(criarIdEmprestimo(listaEmprestimo)),30000.00,10, TipoFinanciamento.ROTATIVO,pessoa);
        listaEmprestimo.add(emprestimo);

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
            System.out.print("Informe uma das opções acima: ");
            opcao = scanner.nextInt();
            switch (opcao) {
                case (1)://Cadastrar novo emprestimo
                    pessoa = cadastrarPessoa(scanner, dataSimples, pessoa);
                    emprestimo = cadastrarEmprestimo(scanner, listaEmprestimo, pessoa);
                    listaEmprestimo.add(emprestimo);
                    break;
                case (2)://Realizar emprestimo
                    realizarPagamentoParcelas(scanner, listaEmprestimo);
                    break;
                case (3)://3 - Consultar situação do emprestimo
                    consultarSituacaoEmprestimo(scanner, listaEmprestimo);
                    break;
                case (4)://4 - Calcular valor da mensalidade
                    consultarMensalidadeContrato(scanner, listaEmprestimo);
                    break;
                case (5)://5 - Calcular valor total do financiamento
                    consultarValorTotalContrato(scanner, listaEmprestimo);
                    break;
                case (6)://6 - Imprimir todos emprestimos
                    listarEmprestimos(listaEmprestimo);
                    break;
                case (7):
                    imprimirValorMenorEmprestimos(listaEmprestimo);
                    break;
                case (8):
                    imprimirValorMaiorEmprestimos(listaEmprestimo);
                    break;
                case (9):
                    imprimirValorMédioEmprestimos(listaEmprestimo);
                    break;
                case (10)://10- Imprimir valor total dos emprestimos
                    imprimirValorTotalEmprestimos(listaEmprestimo);
                    break;
            }
        }while (opcao != 0);
        System.out.println("Fim de programa!");
    }

    private static Emprestimo cadastrarEmprestimo(Scanner scanner, ArrayList<Emprestimo> listaEmprestimo, Pessoa pessoa) {
        TipoFinanciamento tipoFinanciamento;
        Emprestimo emprestimo;
        System.out.println("Informe o valor do emprestimo: ");
        Double valorEmprestimo = scanner.nextDouble();
        System.out.println("Informe a quantidade de meses do emprestimo: ");
        int quantidadeMeses = scanner.nextInt();
        System.out.println("Informe o tipo financiamento PESSOAL/ROTATIVO/CONSIGNADO ");
        tipoFinanciamento = TipoFinanciamento.valueOf(scanner.next());
        emprestimo = new Emprestimo(Integer.toString(criarIdEmprestimo(listaEmprestimo)),valorEmprestimo,quantidadeMeses, tipoFinanciamento, pessoa);
        return emprestimo;
    }

    private static Pessoa cadastrarPessoa(Scanner scanner, SimpleDateFormat dataSimples, Pessoa pessoa) {
        Date data;
        System.out.print("Informe o nome: ");
        scanner.nextLine();
        String nome = scanner.nextLine().trim().toUpperCase();
        String telefone = validarCamposDeTextos("Informe o telefone: ", scanner);
        String tipoPessoaInformado;
        do {
            tipoPessoaInformado = validarCamposDeTextos(
                            "F - Pessoa fisica "+
                            "A - Pessoa fisica aposentado "+
                            "J - Pessoa juridica " +
                            "Informe o tipo da pessoa:", scanner);
        } while (!tipoPessoaInformado.equals("A") && !tipoPessoaInformado.equals("F") && !tipoPessoaInformado.equals("J"));
            if (tipoPessoaInformado.equals("A")) {
                String id = validarCamposDeTextos("Informe o Cpf: ", scanner);
                String tituloEleitor = validarCamposDeTextos("Informe o título de Eleitor: ", scanner);
                pessoa = new PessoaFisica(nome, telefone, id, tituloEleitor);

            } else if(tipoPessoaInformado.equals("F")) {
                String id = validarCamposDeTextos("Informe o Cpf: ", scanner);
                String tituloEleitor = validarCamposDeTextos("Informe o título de Eleitor: ", scanner);
                try {
                    System.out.print("Informe a data da aposentadoria (dd/MM/yyyy): ");
                    data = dataSimples.parse(scanner.next());
                    pessoa = new PessoaFisicaAposentada(nome, telefone, id, tituloEleitor, data);
                }catch (Exception e){
                    System.err.print("Data invalida da aposentaria inválida");
                }
            } else {
                String id = validarCamposDeTextos("Informe o Cnpj: ", scanner);
                String inscricaoMunicipal = validarCamposDeTextos("Informe a inscrição municipal: ", scanner);
                pessoa = new PessoaJuridica(nome, telefone,id,inscricaoMunicipal);
            }
        return pessoa;
    }

    /*public static TipoPessoa validarEscolha(String msg, Scanner scanner, ArrayList<Enum> lista ) {
        boolean valido = false;
        String campoInformado;
        System.out.print(msg);
        do {
            campoInformado = String.valueOf(scanner.nextLine().toUpperCase().charAt(0));


            if (!valido) {
                System.err.print("\nCampo inválido!\n"
                        +msg );
            }
        } while (!valido);
        return campoInformado;
    }*/
    public static String validarCamposDeTextos(String msg, Scanner scanner ) {
        boolean valido = true;
        String campoInformado;
        do {
            System.out.print(msg);
            campoInformado = scanner.next().toUpperCase().trim();
            if (campoInformado.isBlank()) {
                System.err.print("Campos inválido: O campo está em branco \n");
                valido = false;
            }
        } while (!valido);
        return campoInformado;
    }
    public static int criarIdEmprestimo(ArrayList<Emprestimo> listaEmprestimo){
        return (listaEmprestimo.size() == 0) ? 1 : listaEmprestimo.size() + 1;
    }
    public static Emprestimo buscarEmprestimoPorId(ArrayList<Emprestimo> listaEmprestimo, String idEmprestimo){
        for (Emprestimo emprestimo : listaEmprestimo) {
            if (emprestimo.getId().equalsIgnoreCase(idEmprestimo))
                return emprestimo;
        }
        return null;
    }

    private static void realizarPagamentoParcelas(Scanner scanner, ArrayList<Emprestimo> listaEmprestimo) {
        Emprestimo emprestimo;
        String idEmprestimo = validarCamposDeTextos("Informe o número do financiamento que deseja pagar: ", scanner);
        emprestimo = buscarEmprestimoPorId(listaEmprestimo, idEmprestimo);
        if (emprestimo != null){
            try{
                int quantidadeParcelasPagas;
                System.out.print("Informe a quantidade de parcelas para pagamento: ");
                quantidadeParcelasPagas = scanner.nextInt();
                emprestimo.realizarPagamento(quantidadeParcelasPagas);
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
        String idEmprestimo = validarCamposDeTextos("Informe o número do emprestimo para consultar a mensalidade: ", scanner);
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
        String idEmprestimo = validarCamposDeTextos("Informe o número do emprestimo para consultar a mensalidade: ", scanner);
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
        String idEmprestimo = validarCamposDeTextos("Informe o número do financiamento que consultar a situacao: ", scanner);
        emprestimo = buscarEmprestimoPorId(listaEmprestimo, idEmprestimo);
        if (emprestimo != null){
            System.out.println(emprestimo.consultarSituacaoContrato()? "CONTRATO QUITADO":"CONTRATO M ABERTO");
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