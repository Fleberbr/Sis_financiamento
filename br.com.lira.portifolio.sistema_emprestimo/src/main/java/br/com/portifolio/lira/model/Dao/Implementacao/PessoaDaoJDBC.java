package br.com.portifolio.lira.model.Dao.Implementacao;


import br.com.portifolio.lira.model.Dao.PessoaDao;
import br.com.portifolio.lira.model.db.Database;
import br.com.portifolio.lira.model.db.DbException;

import br.com.portifolio.lira.model.entities.Pessoa;
import br.com.portifolio.lira.model.entities.PessoaFisica;
import br.com.portifolio.lira.model.entities.PessoaFisicaAposentada;
import br.com.portifolio.lira.model.entities.PessoaJuridica;
import br.com.portifolio.lira.model.enums.TipoPessoa;

import java.sql.*;

import java.util.List;

public class PessoaDaoJDBC implements PessoaDao {

    private Connection conexao;

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
            if  (TipoPessoa.PESSOA_JURIDICA.equals(pessoa.getTipoPessoa())) {
                statement.setString(4, ((PessoaJuridica) pessoa).getInscricaoMunicipal());
                statement.setString(5, (null));
                statement.setString(6, (null));
            } else{
                statement.setString(4, (null));//inscricação municipal
                if (TipoPessoa.PESSOA_FISICA.equals(pessoa.getTipoPessoa())){
                    statement.setString(5, ((PessoaFisica) pessoa).getTituloEleitor());
                    statement.setString(6, null);
                }
                else {
                    statement.setString(5, ((PessoaFisicaAposentada) pessoa).getTituloEleitor());
                    statement.setDate(6, new java.sql.Date(((PessoaFisicaAposentada) pessoa).getDataAposentadoria().getTime()));
                }
            }
            statement.setString(7, pessoa.getTipoPessoa().name());

            int linhasAlteradas = statement.executeUpdate();
            if (linhasAlteradas > 0) {
                System.out.println("Cliente cadastrado com sucesso.");
            }
            else {
                throw new DbException("Erro inesperado, nenhuma linha foi alterada");
            }
        }catch (SQLException | ClassCastException e){
            throw new DbException(e.getMessage());
        }finally {
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
                    + "WHERE Id = ?" );
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getTelefone());
            if  (TipoPessoa.PESSOA_JURIDICA.equals(pessoa.getTipoPessoa())) {
                statement.setString(3, ((PessoaJuridica) pessoa).getInscricaoMunicipal());
                statement.setString(4, (null));
                statement.setString(5, (null));
            } else{
                statement.setString(3, (null));//inscricação municipal
                if (TipoPessoa.PESSOA_FISICA.equals(pessoa.getTipoPessoa())){
                    statement.setString(4, ((PessoaFisica) pessoa).getTituloEleitor());
                    statement.setString(5, null);
                }
                else {
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

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Pessoa findById(Integer id) {
        return null;
    }

    @Override
    public List<Pessoa> findAll() {
        return null;
    }

    @Override
    public List<Pessoa> findByPessoa(Pessoa pessoa) {
        return null;
    }
}
