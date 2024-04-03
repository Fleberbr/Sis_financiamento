package br.com.portifolio.model.Dao;
import br.com.portifolio.model.entities.Emprestimo;
import br.com.portifolio.model.entities.Pessoa;

import java.util.List;

public interface EmprestimoDao {

    public void insert(Emprestimo emprestimo) ;
    public void update(Emprestimo emprestimo);
    public void deleteById(Integer id);
    public Emprestimo findById(Integer id);
    List<Emprestimo> findAll();
    List<Emprestimo> findByPessoa(Pessoa pessoa);
}