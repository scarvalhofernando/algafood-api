package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long usuarioId){
        this(String.format("Não existe um cadastro de usuário com código %d", usuarioId));
    }
}
