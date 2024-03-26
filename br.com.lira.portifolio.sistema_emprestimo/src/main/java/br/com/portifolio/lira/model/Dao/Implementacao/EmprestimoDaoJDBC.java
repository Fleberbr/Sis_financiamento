package br.com.portifolio.lira.model.Dao.Implementacao;

import br.com.portifolio.lira.model.Dao.EmprestimoDao;
import br.com.portifolio.lira.model.db.Database;
import br.com.portifolio.lira.model.db.DbException;
import br.com.portifolio.lira.model.entities.*;
import br.com.portifolio.lira.model.enums.TipoFinanciamento;
import br.com.portifolio.lira.model.enums.TipoPessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                System.out.println("Emprestimo cadastrado com sucesso.");
            } else {
                throw new DbException("Erro inesperado, nenhuma linha foi alterada.");
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
                    " WHERE id = ?");
            statement.setInt(1, emprestimo.getQuantidadeMeses());
            statement.setInt(2, emprestimo.getQuantidadeParcelasPagas());
            statement.setDouble(3, emprestimo.getValorJuros());
            statement.setDouble(4, emprestimo.getValorEmprestimo());
            statement.setDouble(5, emprestimo.getValorParcela());
            statement.setDouble(6, emprestimo.getValorTotalEmprestimo());
            statement.setString(7, emprestimo.getTipoFinanciamento().name());
            //statement.setString(8, emprestimo.getPessoa().getId());
            statement.setInt(8, emprestimo.getId());

            System.out.println(statement.getResultSet());
            int linhasAlteradas = statement.executeUpdate();
            if (linhasAlteradas > 0) {
                System.out.println("Emprestimo atualizado !");
            } else {
                throw new DbException("Erro inesperado, nenhuma linha foi alterada");
            }
        } catch (SQLException e) {
            throw new DbException("Erro inesperado "+ e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }


    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;
        try {
            statement = conexao.prepareStatement("DELETE FROM EMPRESTIMO " +
                    "WHERE Id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();

            //int linhasAlteradas, pode ser usado para testar se foi deletado algum registro.
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public Emprestimo findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexao.prepareStatement(
                    "SELECT EMPRESTIMO.*, " +
                            "PESSOA.* " +
                            "FROM EMPRESTIMO INNER JOIN PESSOA " +
                            "ON EMPRESTIMO.IdPessoa = pessoa.Id " +
                            "WHERE EMPRESTIMO.Id = ? ");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            //encontrou registro
            if (resultSet.next()) {
                Pessoa pessoa = instanciarPessoa(resultSet);
                return instanciarEmprestimo(resultSet, pessoa);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }


    @Override
    public List<Emprestimo> findAll() {
        List<Emprestimo> emprestimoList = new ArrayList<>();
        Pessoa pessoa ;
        Emprestimo emprestimo ;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conexao.prepareStatement(
                    "SELECT EMPRESTIMO.*, " +
                            "PESSOA.* " +
                            "FROM EMPRESTIMO INNER JOIN PESSOA " +
                            "ON EMPRESTIMO.IdPessoa = pessoa.Id ");

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pessoa = instanciarPessoa(resultSet);
                emprestimo = instanciarEmprestimo(resultSet, pessoa);
                emprestimoList.add(emprestimo);
            }
            return emprestimoList;
            /*evita instanciar varios objetos Pessoa
            Map<Integer, Emprestimo> map = new HashMap<>();
            while (resultSet.next()) {
                Emprestimo emprestimo = map.get(resultSet.getInt("Id"));//Existe alguma pessoa com esse id.
                if (emprestimo == null) {
                    Pessoa pessoa = instanciarPessoa(resultSet);
                    emprestimo = instanciarEmprestimo(resultSet,pessoa);
                    map.put(emprestimo.getId(), emprestimo);
                }
                emprestimoList.add(emprestimo);
            }*/
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Emprestimo> findByPessoa(Pessoa pessoa) {
        List<Emprestimo> emprestimoList = new ArrayList<>();
        Emprestimo emprestimo = null ;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conexao.prepareStatement(
                    "SELECT * " +
                            "FROM EMPRESTIMO  INNER JOIN PESSOA " +
                            "ON EMPRESTIMO.idPessoa = PESSOA.Id " +
                            "WHERE PESSOA.Id = ? ");
            statement.setString(1, pessoa.getId());
            resultSet = statement.executeQuery();


            //evita instanciar varios objetos Pessoa
            Map<String, Pessoa> map = new HashMap<>();
            while (resultSet.next()) {
                pessoa = map.get(resultSet.getString("Id"));//Existe alguma pessoa com esse id.
                if (pessoa == null) {
                    pessoa = instanciarPessoa(resultSet);
                    emprestimo = instanciarEmprestimo(resultSet,pessoa);
                    map.put(pessoa.getId(), pessoa);
                }
                emprestimoList.add(emprestimo);
            }
            return emprestimoList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }


    private Pessoa instanciarPessoa(ResultSet resultSet) throws SQLException {
        if (TipoPessoa.PESSOA_FISICA.name().equals((resultSet.getString("tipoPessoa")))) {
            return new PessoaFisica(
                    resultSet.getString("nome"),
                    resultSet.getString("telefone"),
                    resultSet.getString("Id"),
                    resultSet.getString("tituloEleitor"),
                    TipoPessoa.PESSOA_FISICA);
        } else if (TipoPessoa.PESSOA_FISICA_APOSENTADA.name().equals(resultSet.getString("tipoPessoa"))) {
            return new PessoaFisicaAposentada(
                    resultSet.getString("nome"),
                    resultSet.getString("telefone"),
                    resultSet.getString("Id"),
                    resultSet.getString("tituloEleitor"),
                    resultSet.getDate("dataAposentadoria"));

        } else {
            return new PessoaJuridica(
                    resultSet.getString("nome"),
                    resultSet.getString("telefone"),
                    resultSet.getString("Id"),
                    resultSet.getString("inscricaoMunicipal"));
        }
    }

    private Emprestimo instanciarEmprestimo(ResultSet resultSet, Pessoa pessoa) throws SQLException {
        return new Emprestimo(
                resultSet.getInt("id"),
                resultSet.getDouble("valorEmprestimo"),
                resultSet.getInt("quantidadeMeses"),
                resultSet.getInt("quantidadeParcelasPagas"),
                resultSet.getInt("valorJuros"),
                resultSet.getInt("valorTotalEmprestimo"),
                resultSet.getInt("valorParcela"),
                TipoFinanciamento.valueOf(resultSet.getString("tipoFinanciamento")),
                pessoa);
    }
}
