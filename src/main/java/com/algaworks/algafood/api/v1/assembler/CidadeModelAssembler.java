package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTO toDTO(Cidade cidade){
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> toCollectionModel(List<Cidade> cidades){
        return cidades.stream()
                .map(cidade -> toDTO(cidade))
                .collect(Collectors.toList());
    }
}
