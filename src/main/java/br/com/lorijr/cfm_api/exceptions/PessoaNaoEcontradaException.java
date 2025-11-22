package br.com.lorijr.cfm_api.exceptions;

public class PessoaNaoEcontradaException extends RecursoNaoEncontradoException {
    public PessoaNaoEcontradaException(String message) {
        super(message);
    }
}
