package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
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
	public void testarCadastroCozinhaComSucesso(){
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
    public void testarCadastroCozinhaSemNome(){
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        novaCozinha = cadastroCozinha.salvar(novaCozinha);
    }
}
