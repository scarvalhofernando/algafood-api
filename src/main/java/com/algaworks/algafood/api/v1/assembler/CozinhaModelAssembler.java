package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO toDto(Cozinha cozinha){
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    public List<CozinhaDTO> toCollectionModel(List<Cozinha> cozinhas){
        return cozinhas.stream()
                .map(cozinha -> toDto(cozinha))
                .collect(Collectors.toList());
    }
}
