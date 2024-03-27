package br.com.portifolio.lira.service;

import br.com.portifolio.lira.model.Dao.DaoFactory;
import br.com.portifolio.lira.model.Dao.PessoaDao;
import br.com.portifolio.lira.model.entities.Pessoa;

public class PessoaService {


    static PessoaDao pessoaDao = DaoFactory.createPessoaDAO();


    public void cadastrarPessoa(Pessoa pessoa){

        if(pessoaDao.findById(pessoa.getId()) == null) {
            pessoaDao.insert(pessoa);
        }
        else{
            System.err.println("Já existe um cliente cadastrado para esse cpf/cnpj,segue dados abaixo:");
            System.out.println(pessoa);
        }
    }

    public static Pessoa buscarPessoa(String id) {
        return pessoaDao.findById(id);
    }

    public static void imprimirListaPessoa() {
        System.out.println("+++++++ INICIO LISTA DE CLIENTES +++++++");
        pessoaDao.findAll().forEach(System.out::println);
        System.out.println("++++++++++ FIM LISTA DE CLIENTES +++++++");
    }
}
