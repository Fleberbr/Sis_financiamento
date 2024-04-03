package br.com.portifolio.lira.model.db;

public class DbIntegrityException extends RuntimeException{

    public DbIntegrityException(String msg){
        super(msg);
    }
}
