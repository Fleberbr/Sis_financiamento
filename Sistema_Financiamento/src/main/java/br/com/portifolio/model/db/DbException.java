package br.com.portifolio.model.db;

public class DbException extends RuntimeException{

    public DbException(String msg){
        super(msg);
    }
}
