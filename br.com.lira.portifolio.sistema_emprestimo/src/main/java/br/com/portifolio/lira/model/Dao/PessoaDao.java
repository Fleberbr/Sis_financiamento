package br.com.portifolio.lira.model.Dao;

import br.com.portifolio.lira.model.entities.Emprestimo;
import java.util.List;

public interface EmprestimoDao {

    public void insert(Emprestimo emprestimo) ;
    public void update(Emprestimo emprestimo);
    public void deleteById(Integer id);
    public Emprestimo findById(Integer id);
    List<Emprestimo> findAll();
    List<Emprestimo> findByDepartment(Emprestimo emprestimo);
}
