package br.com.portifolio.lira.repository;

import br.com.portifolio.lira.model.entities.*;
import br.com.portifolio.lira.model.enums.TipoFinanciamento;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class emprestimoRepository {

    public static String path = "C:\\Users\\elton\\OneDrive\\Documentos\\Projetos programação\\ws-eclipse\\sisEmprestimo\\br.com.lira.portifolio.sistema_emprestimo";
    public static String[] vetorLinha;
    public static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    public static Pessoa pessoa;

    public static String getPath() {
        return path;
    }

    public static boolean criarArquivoEmprestimo() {
        boolean resultado = false;
        //String path = "C:\\Users\\elton\\OneDrive\\Documentos\\Projetos programação\\ws-eclipse\\sisEmprestimo\\br.com.lira.portifolio.sistema_emprestimo";
        //boolean resultado = new File(path + "\\out").mkdir();
        //System.out.println((resultado)? "Diretório criado com sucesso" : "Diretório já existe.");;
        try {
            resultado = new File(getPath() + "\\emprestimo.csv").createNewFile();
            System.out.println((resultado) ? "Arquivo emprestimo criado com sucesso " : "Arquivo já existe");
        } catch (IOException ex) {
            System.err.println("Não foi possível criar o arquivo emprestimo, arquivo aberto");
        } finally {
            return resultado;
        }
    }

    public static boolean deletarArquivoEmprestimo() {
        boolean resultado = new File(getPath() + "\\emprestimo.csv").delete();
        System.out.println((resultado) ? "Arquivo deletado com sucesso" : "Não foi possível deletar o arquivo");
        return resultado;
    }

    public static void inserirNovoRegistroArquivoEmprestimo(Emprestimo emprestimo) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getPath() + "\\emprestimo.csv", true))) {
            bufferedWriter.write(emprestimo.formatarDadosEmprestimoInsercaoArquivo());
            bufferedWriter.newLine();
            System.out.println("Dados emprestimo inseridos com sucesso");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void AtualizarRegistroArquivoEmprestimo(ArrayList<Emprestimo> listaEmprestimo) {
        deletarArquivoEmprestimo();
        criarArquivoEmprestimo();
        for (Emprestimo emprestimo : listaEmprestimo) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getPath() + "\\emprestimo.csv", true))) {
                bufferedWriter.write(emprestimo.formatarDadosEmprestimoInsercaoArquivo());
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Emprestimo> buscarDadosEmprestimo() {
        ArrayList<Emprestimo> listaEmprestimo = new ArrayList<>();

        //CSVReader reader = new CSVReader(new FileReader(inputFile), ',');

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getPath() + "\\emprestimo.csv"))) {
            System.out.println("lendo arquivo de entrada");
            String linha = bufferedReader.readLine();
            while (linha != null) {
                //Aqui cria um vetorLinha e lê o arquivo CSV e quebra em variáveis por indice.
                vetorLinha = linha.split(",");
                int idContrato = Integer.parseInt(vetorLinha[0]);
                double valorEmprestimo = Double.parseDouble(vetorLinha[1]);
                int quantidadeMeses = Integer.parseInt(vetorLinha[2]);
                int quantidadeParcelasPagas = Integer.parseInt(vetorLinha[3]);
                String tipoFinanciamento = (vetorLinha[4]);
                String tipoPessoa = vetorLinha[5];
                String id = vetorLinha[6];
                String nome = vetorLinha[7];
                String telefone = vetorLinha[8];
                String tituloEleitor;
                Date dataAposentadoria;
                String inscriçãoMunicipal;
                switch (tipoPessoa) {
                    case ("PESSOA_FISICA"):
                        tituloEleitor = vetorLinha[9];
                        pessoa = new PessoaFisica(id, nome, telefone, tituloEleitor);
                        break;

                    case ("PESSOA_FISICA_APOSENTADA"):
                        tituloEleitor = vetorLinha[9];
                        dataAposentadoria = formato.parse(vetorLinha[10]);
                        pessoa = new PessoaFisicaAposentada(id, nome, telefone, tituloEleitor, dataAposentadoria);
                        break;

                    case ("PESSOA_JURIDICA"): {
                        inscriçãoMunicipal = vetorLinha[9];
                        pessoa = new PessoaJuridica(id, nome, telefone, inscriçãoMunicipal);
                        break;
                    }
                }
                listaEmprestimo.add(new Emprestimo(idContrato, valorEmprestimo, quantidadeMeses, quantidadeParcelasPagas, TipoFinanciamento.valueOf(tipoFinanciamento), pessoa));
                linha = bufferedReader.readLine();
            }
        } catch (IOException | ParseException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return listaEmprestimo;
    }
}


