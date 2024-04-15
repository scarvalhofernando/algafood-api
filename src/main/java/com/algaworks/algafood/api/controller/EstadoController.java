package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import com.algaworks.algafood.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    private List<EstadoDTO> listar(){
        List<Estado> todosEstados = estadoRepository.findAll();
        return estadoModelAssembler.toCollectionModel(todosEstados);
    }

    @GetMapping("/{estadoId}")
    public EstadoDTO buscar(@PathVariable Long estadoId){
        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
        return estadoModelAssembler.toDTO(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput){
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
        estado = cadastroEstado.salvar(estado);
        return estadoModelAssembler.toDTO(estado);
    }

    @PutMapping("/{estadoId}")
    public Estado atualizar(@PathVariable Long estadoId,
                                            @RequestBody @Valid EstadoInput estadoInput){
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
        estadoAtual = cadastroEstado.salvar(estadoAtual);
        return estadoModelAssembler.toDTO(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> remover(@PathVariable Long estadoId){
        try {
            cadastroEstado.excluir(estadoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
}