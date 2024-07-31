package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDTO toDto(FormaPagamento formaPagamento){
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> toCollectionModel(Collection<FormaPagamento> formaPagamentos){
        return formaPagamentos.stream()
                .map(formaPagamento -> toDto(formaPagamento))
                .collect(Collectors.toList());
    }
}
