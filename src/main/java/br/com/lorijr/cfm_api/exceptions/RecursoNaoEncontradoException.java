package br.com.lorijr.cfm_api.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException{
    public RecursoNaoEncontradoException(String message){
        super(message);
    }
}
