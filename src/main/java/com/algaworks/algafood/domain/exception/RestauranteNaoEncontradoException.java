package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long restauranteId){
        this(String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
    }
}
