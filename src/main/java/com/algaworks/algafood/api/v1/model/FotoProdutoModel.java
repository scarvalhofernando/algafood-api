package com.algaworks.algafood.api.v1.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoModel {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
