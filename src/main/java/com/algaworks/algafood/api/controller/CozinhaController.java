package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public List<CozinhaDTO> listar(){
        List<Cozinha> todasCozinhas = cozinhaRepository.findAll();

        return cozinhaModelAssembler.toCollectionModel(todasCozinhas);
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable("cozinhaId") Long cozinhaId){
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

        return cozinhaModelAssembler.toDto(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = cadastroCozinha.salvar(cozinha);

        return cozinhaModelAssembler.toDto(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(@PathVariable Long cozinhaId,
                                             @RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);

        return cozinhaModelAssembler.toDto(cozinhaAtual);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId){
        try {
            cadastroCozinha.excluir(cozinhaId);
        } catch(EntidadeNaoEncontradaException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}