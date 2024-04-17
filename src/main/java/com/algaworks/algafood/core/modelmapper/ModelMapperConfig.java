package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.EnderecoDTO;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        var enderecoModelTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoDTO.class);

        enderecoModelTypeMap.addMapping(
                src -> src.getCidade().getEstado().getNome(),
                (dest, value) -> dest.getCidade().setEstado((EstadoDTO) value));

        return modelMapper;
    }
}
