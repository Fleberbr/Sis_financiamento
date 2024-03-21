package br.com.portifolio.lira.model.Dao.Implementacao;

import br.com.portifolio.lira.model.Dao.EmprestimoDao;
import br.com.portifolio.lira.model.db.Database;
import br.com.portifolio.lira.model.db.DbException;
import br.com.portifolio.lira.model.entities.Emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EmprestimoDaoJDBC implements EmprestimoDao {

    private Connection conexao;

    public EmprestimoDaoJDBC(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void insert(Emprestimo emprestimo) {
        PreparedStatement statement = null;
        try {
            statement = conexao.prepareStatement("INSERT INTO EMPRESTIMO " +
                    " (quantidadeMeses, quantidadeParcelasPagas, valorJuros, valorEmprestimo,  valorParcela,  valorTotalEmprestimo, tipoFinanciamento, idPessoa) " +
                    " VALUES " +
                    " (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, emprestimo.getQuantidadeMeses());
            statement.setInt(2, 0);
            statement.setDouble(3, emprestimo.getValorJuros());
            statement.setDouble(4, emprestimo.getValorEmprestimo());
            statement.setDouble(5, emprestimo.getValorParcela());
            statement.setDouble(6, emprestimo.getValorTotalEmprestimo());
            statement.setString(7, emprestimo.getTipoFinanciamento().name());
            statement.setString(8, emprestimo.getPessoa().getId());

            int linhasAlteradas = statement.executeUpdate();
            if (linhasAlteradas > 0) {
                System.out.println("Emprestimo cadastrado com sucesso");
            } else {
                throw new DbException("Erro inesperado, nenhuma linha foi alterada");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public void update(Emprestimo emprestimo) {
        PreparedStatement statement = null;
        try {
            statement = conexao.prepareStatement("UPDATE EMPRESTIMO " +
                    " SET quantidadeMeses = ?," +
                    " quantidadeParcelasPagas = ?, " +
                    " valorJuros = ?," +
                    " valorEmprestimo = ?, " +
                    " valorParcela = ?, " +
                    " valorTotalEmprestimo = ?," +
                    " tipoFinanciamento = ?," +
                    " idPessoa = ?" +
                    " WHERE Id = ?");
            statement.setInt(1, emprestimo.getQuantidadeMeses());
            statement.setInt(2, emprestimo.getQuantidadeParcelasPagas());
            statement.setDouble(3, emprestimo.getValorJuros());
            statement.setDouble(4, emprestimo.getValorEmprestimo());
            statement.setDouble(5, emprestimo.getValorParcela());
            statement.setDouble(6, emprestimo.getValorTotalEmprestimo());
            statement.setString(7, emprestimo.getTipoFinanciamento().name());
            statement.setString(8, emprestimo.getPessoa().getId());
            statement.setInt(9,emprestimo.getId());

            int linhasAlteradas = statement.executeUpdate();
            if (linhasAlteradas > 0) {
                System.out.println("Emprestimo atualizado !");
            } else {
                throw new DbException("Erro inesperado, nenhuma linha foi alterada");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }


    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Emprestimo findById(Integer id) {
        return null;
    }

    @Override
    public List<Emprestimo> findAll() {
        return null;
    }

    @Override
    public List<Emprestimo> findByEmprestimo(Emprestimo emprestimo) {
        return null;
    }

}
