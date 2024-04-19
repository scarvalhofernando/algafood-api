package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.EnderecoDTO;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

//    @Bean
//    public ModelMapper modelMapper() {
//        var modelMapper = new ModelMapper();
//
////		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
////			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
//
//        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
//                Endereco.class, EnderecoDTO.class);
//
//        enderecoToEnderecoModelTypeMap.addMapping(
//                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
//                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado((EstadoDTO) value));
//
//        return modelMapper;
//    }

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        // Criar um mapeamento vazio de Endereco para EnderecoDTO
        var enderecoTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);

        // Adicionar os mapeamentos explícitos
        enderecoTypeMap.addMapping(
                src -> src.getCidade().getEstado().getId(),
                (dest, value) -> dest.getCidade().getEstado().setId((Long) value));

        enderecoTypeMap.addMapping(
                src -> src.getCidade().getEstado().getNome(),
                (dest, value) -> dest.getCidade().getEstado().setNome((String) value));

        // Retornar o ModelMapper configurado
        return modelMapper;
    }
}
