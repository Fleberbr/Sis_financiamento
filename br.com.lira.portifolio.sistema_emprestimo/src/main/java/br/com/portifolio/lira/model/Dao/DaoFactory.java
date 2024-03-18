package br.com.portifolio.lira.model.Dao;
import br.com.portifolio.lira.model.db.Database;
import db.Database;
import model.dao.implementacao.DepartmentDaoJDBC;
import model.dao.implementacao.SellerDaoJDBC;

public class DaoFactory {

    //Retorna a conexão com o banco de dados
    public static EmprestimoDao createSellerDAO(){
        return new EmprestimoDaoJDBC(Database.getConexao());
    }


}
