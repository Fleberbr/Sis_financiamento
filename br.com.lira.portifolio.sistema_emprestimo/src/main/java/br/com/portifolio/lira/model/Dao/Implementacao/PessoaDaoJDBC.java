package br.com.portifolio.lira.model.Dao.Implementacao;


import br.com.portifolio.lira.model.Dao.PessoaDao;
import br.com.portifolio.lira.model.db.Database;
import br.com.portifolio.lira.model.db.DbException;

import br.com.portifolio.lira.model.entities.*;
import br.com.portifolio.lira.model.enums.TipoPessoa;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PessoaDaoJDBC implements PessoaDao {

    private Connection conexao;

    public PessoaDaoJDBC(){}
    public PessoaDaoJDBC(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public void insert(Pessoa pessoa) {
        PreparedStatement statement = null;
        try {
            statement = conexao.prepareStatement("INSERT INTO pessoa " +
                    " (id, nome, telefone, inscricaoMunicipal, tituloEleitor, dataAposentadoria, tipoPessoa) " + //, tituloEleitor, dataAposentadoria
                    " VALUES " +
                    " (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, pessoa.getId());
            statement.setString(2, pessoa.getNome());
            statement.setString(3, pessoa.getTelefone());
            if (TipoPessoa.PESSOA_JURIDICA.equals(pessoa.getTipoPessoa())) {
                statement.setString(4, ((PessoaJuridica) pessoa).getInscricaoMunicipal());
                statement.setString(5, (null));
                statement.setString(6, (null));
            } else {
                statement.setString(4, (null));//inscricação municipal
                if (TipoPessoa.PESSOA_FISICA.equals(pessoa.getTipoPessoa())) {
                    statement.setString(5, ((PessoaFisica) pessoa).getTituloEleitor());
                    statement.setString(6, null);
                } else {
                    statement.setString(5, ((PessoaFisicaAposentada) pessoa).getTituloEleitor());
                    statement.setDate(6, new java.sql.Date(((PessoaFisicaAposentada) pessoa).getDataAposentadoria().getTime()));
                }
            }
            statement.setString(7, pessoa.getTipoPessoa().name());

            int linhasAlteradas = statement.executeUpdate();
            if (linhasAlteradas > 0) {
                System.out.println("Cliente cadastrado com sucesso.");
            } else {
                throw new DbException("Falha no cadastro do cliente");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }

    }

    @Override
    public void update(Pessoa pessoa) {
        PreparedStatement statement = null;
        try {
            statement = conexao.prepareStatement("UPDATE pessoa " +
                    " SET nome = ?," +
                    " telefone = ?," +
                    " inscricaoMunicipal = ?," +
                    " tituloEleitor = ?," +
                    " dataAposentadoria = ?," +
                    " tipoPessoa = ? "
                    + "WHERE Id = ?");
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getTelefone());
            if (TipoPessoa.PESSOA_JURIDICA.equals(pessoa.getTipoPessoa())) {
                statement.setString(3, ((PessoaJuridica) pessoa).getInscricaoMunicipal());
                statement.setString(4, (null));
                statement.setString(5, (null));
            } else {
                statement.setString(3, (null));//inscricação municipal
                if (TipoPessoa.PESSOA_FISICA.equals(pessoa.getTipoPessoa())) {
                    statement.setString(4, ((PessoaFisica) pessoa).getTituloEleitor());
                    statement.setString(5, null);
                } else {
                    statement.setString(4, ((PessoaFisicaAposentada) pessoa).getTituloEleitor());
                    statement.setDate(5, new java.sql.Date(((PessoaFisicaAposentada) pessoa).getDataAposentadoria().getTime()));
                }
            }
            statement.setString(6, pessoa.getTipoPessoa().name());
            statement.setString(7, pessoa.getId());
            int linhasAlteradas = statement.executeUpdate();
            if (linhasAlteradas > 0) {
                System.out.println("Dados cadastrais atualizado com sucesso!");
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
    public void deleteById(String id) {
        PreparedStatement statement = null;
        try {
            statement = conexao.prepareStatement("DELETE FROM PESSOA " +
                    "WHERE Id = ?");
            statement.setString(1, id);
            statement.executeUpdate();

            //int linhasAlteradas, pode ser usado para testar se foi deletado algum registro.
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public Pessoa findById(String id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexao.prepareStatement(
                        " SELECT * " +
                            " FROM PESSOA " +
                            " WHERE Id = ? ");
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            //encontrou registro
            if (resultSet.next()) {
                return instanciarPessoa(resultSet);

            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }

    protected Pessoa instanciarPessoa(ResultSet resultSet) throws SQLException {
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

    @Override
    public List<Pessoa> findAll() {
        List<Pessoa> pessoaList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conexao.prepareStatement(
                    " SELECT * FROM PESSOA ");

            resultSet = statement.executeQuery();

            //evita instanciar varios objetos Pessoa
            Map<String, Pessoa> map = new HashMap<>();
            while (resultSet.next()) {
                Pessoa pessoa = map.get(resultSet.getString("Id"));//Existe alguma pessoa com esse id.
                if (pessoa == null) {
                    pessoa = instanciarPessoa(resultSet);
                    map.put(pessoa.getId(), pessoa);
                }
                pessoaList.add(pessoa);
            }
            /*Solução simples #2
            while(resultSet.next()) {
                Seller seller = instanciarSeller(resultSet, department);
                sellerList.add(seller);
            }*/
            return pessoaList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
    }
}
