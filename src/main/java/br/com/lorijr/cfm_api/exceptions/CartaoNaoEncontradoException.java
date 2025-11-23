package br.com.lorijr.cfm_api.exceptions;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

public class CartaoNaoEncontradoException extends RecursoNaoEncontradoException {
    public CartaoNaoEncontradoException(String message) {
        super(message);
    }
}
