package br.com.portifolio.lira.model.Dao;

import br.com.portifolio.lira.model.entities.Pessoa;
import java.util.List;

public interface PessoaDao {

    public void insert(Pessoa pessoa) ;
    public void update(Pessoa pessoa);
    public void deleteById(Integer id);
    public Pessoa findById(Integer id);
    List<Pessoa> findAll();
    List<Pessoa> findByPessoa(Pessoa Pessoa);
}