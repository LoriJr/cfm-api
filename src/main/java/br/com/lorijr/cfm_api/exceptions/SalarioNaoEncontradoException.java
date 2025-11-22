package br.com.lorijr.cfm_api.exceptions;

public class SalarioNaoEncontradoException extends RecursoNaoEncontradoException{
    public SalarioNaoEncontradoException(String message) {
        super(message);
    }
}
