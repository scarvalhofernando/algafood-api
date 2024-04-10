package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos(){
//        cenário
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

//        ação
        novaCozinha = cadastroCozinha.salvar(novaCozinha);

//        validação
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @org.junit.Test(expected = ConstraintViolationException.class)
    public void deveFlahar_QuandoCadastrarCozinhaSemNome(){
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        novaCozinha = cadastroCozinha.salvar(novaCozinha);
    }

//    @Test(expected = EntidadeEmUsoException.class)
//    public void deveFalhar_QuandoExcluirCozinhaEmUso(){
//        cadastroCozinha.excluir(1L);
//    }

//    @Test(expected = CozinhaNaoEncontradaException.class)
//    public void deveFalhar_QuandoExcluirCozinhaInexistente(){
//        cadastroCozinha.excluir(100L);
//    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso(){
        EntidadeEmUsoException erroEsperado =
                org.junit.jupiter.api.Assertions.assertThrows(EntidadeEmUsoException.class, () ->{
                    cadastroCozinha.excluir(1L);
                });
        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente(){
        CozinhaNaoEncontradaException erroEsperado =
                org.junit.jupiter.api.Assertions.assertThrows(CozinhaNaoEncontradaException.class, () ->{
                    cadastroCozinha.excluir(100L);
                });
        assertThat(erroEsperado).isNotNull();
    }
}
