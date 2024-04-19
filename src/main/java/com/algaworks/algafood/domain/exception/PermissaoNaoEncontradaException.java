package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public static final long serialVersionUID = 1L;

    public PermissaoNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long permissaoId){
        this(String.format("Não existe um cadastro de permissão com código %d", permissaoId));
    }
}
