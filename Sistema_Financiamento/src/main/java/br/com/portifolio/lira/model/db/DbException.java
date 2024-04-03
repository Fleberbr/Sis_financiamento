package br.com.portifolio.lira.model.db;

public class DbException extends RuntimeException{

    public DbException(String msg){
        super(msg);
    }
}
