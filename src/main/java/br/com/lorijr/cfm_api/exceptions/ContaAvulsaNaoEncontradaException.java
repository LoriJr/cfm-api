package br.com.lorijr.cfm_api.exceptions;

public class ContaAvulsaNaoEncontradaException extends RecursoNaoEncontradoException{
    public ContaAvulsaNaoEncontradaException(String message) {
        super(message);
    }
}
