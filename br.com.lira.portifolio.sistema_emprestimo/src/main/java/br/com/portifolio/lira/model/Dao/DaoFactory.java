package br.com.portifolio.lira.model.Dao;

import br.com.portifolio.lira.model.Dao.Implementacao.EmprestimoDaoJDBC;
import br.com.portifolio.lira.model.Dao.Implementacao.PessoaDaoJDBC;
import br.com.portifolio.lira.model.db.Database;

public class DaoFactory {

    //Retorna a conexão com o banco de dados
    public static EmprestimoDao createEmprestimoDAO(){
        return new EmprestimoDaoJDBC(Database.getConexao());
    }

    //Retorna a conexão com o banco de dados
    public static PessoaDao createPessoaDAO(){
        return new PessoaDaoJDBC(Database.getConexao());
    }

}
