package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public List<RestauranteDTO> listar(){
        return toCollectionDTO(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscar(@PathVariable Long restauranteId){
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return toDto(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(
            @RequestBody @Valid RestauranteInput restauranteInput){
        try {
            Restaurante restaurante = toDomainObject(restauranteInput);
            return toDto(cadastroRestaurante.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId,
                                       @RequestBody @Valid RestauranteInput restauranteInput){
        try {
            Restaurante restaurante = toDomainObject(restauranteInput);
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
            BeanUtils.copyProperties(restaurante, restauranteAtual,
                        "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
            return toDto(cadastroRestaurante.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

//    @PatchMapping("/{restauranteId}")
//    public RestauranteDTO atualizarParcial(
//            @PathVariable Long restauranteId, @RequestBody Map<String, Object> campos, HttpServletRequest request){
//        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
//
//        merge(campos, restauranteAtual, request);
//        validate(restauranteAtual, "restaurante");
//
//        return atualizar(restauranteId, restauranteAtual);
//    }

    private void validate(Restaurante restaurante, String object) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, object);
        validator.validate(restaurante, bindingResult);

        if(bindingResult.hasErrors()){
            throw new ValidationException(String.valueOf(bindingResult));
        }
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            System.out.println(restauranteOrigem);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    private RestauranteDTO toDto(Restaurante restaurante) {
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDTO.setCozinha(cozinhaDTO);

        return restauranteDTO;
    }

    private List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(restaurante -> toDto(restaurante))
                .collect(Collectors.toList());
    }

    private Restaurante toDomainObject(RestauranteInput restauranteInput){
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return restaurante;
    }
}