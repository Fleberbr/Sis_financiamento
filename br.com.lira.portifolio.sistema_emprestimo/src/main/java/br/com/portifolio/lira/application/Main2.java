package br.com.portifolio.lira.application;

import br.com.portifolio.lira.model.Dao.EmprestimoDao;
import br.com.portifolio.lira.model.Dao.Implementacao.EmprestimoDaoJDBC;
import br.com.portifolio.lira.model.Dao.Implementacao.PessoaDaoJDBC;
import br.com.portifolio.lira.model.Dao.PessoaDao;
import br.com.portifolio.lira.model.db.Database;
import br.com.portifolio.lira.model.entities.*;
import br.com.portifolio.lira.model.enums.TipoFinanciamento;
import br.com.portifolio.lira.model.exception.ExceptionError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws ParseException, ExceptionError {

        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dataSimples = new SimpleDateFormat("dd-MM-yyyy");
        int opcao;
        Pessoa pessoa = null;
        Emprestimo emprestimo;

        PessoaDao pessoaDao = new PessoaDaoJDBC(Database.getConexao());
        EmprestimoDao emprestimoDao = new EmprestimoDaoJDBC(Database.getConexao());

        /*System.out.println("Data nascimento: ");
        Date dataNascimento = dataSimples.parse(scanner.next());
        //String dataNascimento = dataSimples.format("11/06/2024");*/

        System.out.print("Informe um id para pesquisa: ");
        int id = scanner.nextInt();

        //pessoa = pessoaDao.findById(id);
        //System.out.println(pessoa);

        emprestimo = emprestimoDao.findById(id);
        System.out.println(emprestimo);


        //pessoa = new PessoaFisicaAposentada("EltonAposentadoMonster","11111","3","222",dataSimples.parse("31-01-2024"));
        //pessoaDao.update(pessoa);
        //pessoaDao.insert(pessoa);
        //emprestimo = new Emprestimo(2,10000.00,10, TipoFinanciamento.CONSIGNADO,pessoa);
        //emprestimo.realizarPagamento(3);
        //emprestimoDao.insert(emprestimo);
        //emprestimoDao.update(emprestimo);


        /*pessoa = new PessoaFisica("EltonFisico","11971","1","111");
        //pessoaDao.insert(pessoa);
        emprestimo = new Emprestimo(null,20000.00,10, TipoFinanciamento.PESSOAL,pessoa);
        emprestimoDao.insert(emprestimo);

        pessoa = new PessoaJuridica("EltonJuridico","11971","2","111");
        //pessoaDao.insert(pessoa);
        emprestimo = new Emprestimo(null,20000.00,10, TipoFinanciamento.PESSOAL,pessoa);
        emprestimoDao.insert(emprestimo);*/



    }
}