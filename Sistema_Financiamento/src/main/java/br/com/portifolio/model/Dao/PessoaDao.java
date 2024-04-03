package br.com.portifolio.model.Dao;

import br.com.portifolio.model.entities.Pessoa;
import java.util.List;

public interface PessoaDao {

    public void insert(Pessoa pessoa) ;
    public void update(Pessoa pessoa);
    public void deleteById(String id);
    public Pessoa findById(String id);
    List<Pessoa> findAll();

}