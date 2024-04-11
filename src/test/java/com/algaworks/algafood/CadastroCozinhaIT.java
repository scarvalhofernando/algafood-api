package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application_test.properties")
public class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Before
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(2));
//                .body("nome", hasItems("Indiana", "Tailandesa"));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
                .body("{ \"nome\": \"Chinesa\" }")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente(){
        given()
                .pathParam("cozinhaId", 2)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo("Americana"));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente(){
        given()
                .pathParam("cozinhaId", 100)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados(){
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cozinhaRepository.save(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Americana");
        cozinhaRepository.save(cozinha2);
    }

// TESTE DE INTEGRAÇÃO
//    @Autowired
//    private CadastroCozinhaService cadastroCozinha;

//    @Test
//	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos(){
////        cenário
//        Cozinha novaCozinha = new Cozinha();
//        novaCozinha.setNome("Chinesa");
//
////        ação
//        novaCozinha = cadastroCozinha.salvar(novaCozinha);
//
////        validação
//        assertThat(novaCozinha).isNotNull();
//        assertThat(novaCozinha.getId()).isNotNull();
//    }
//
//    @org.junit.Test(expected = ConstraintViolationException.class)
//    public void deveFlahar_QuandoCadastrarCozinhaSemNome(){
//        Cozinha novaCozinha = new Cozinha();
//        novaCozinha.setNome(null);
//
//        novaCozinha = cadastroCozinha.salvar(novaCozinha);
//    }
//
//    @Test(expected = EntidadeEmUsoException.class)
//    public void deveFalhar_QuandoExcluirCozinhaEmUso(){
//        cadastroCozinha.excluir(1L);
//    }
//
//    @Test(expected = CozinhaNaoEncontradaException.class)
//    public void deveFalhar_QuandoExcluirCozinhaInexistente(){
//        cadastroCozinha.excluir(100L);
//    }

//    @Test
//    public void deveFalhar_QuandoExcluirCozinhaEmUso(){
//        EntidadeEmUsoException erroEsperado =
//                org.junit.jupiter.api.Assertions.assertThrows(EntidadeEmUsoException.class, () ->{
//                    cadastroCozinha.excluir(1L);
//                });
//        assertThat(erroEsperado).isNotNull();
//    }

//    @Test
//    public void deveFalhar_QuandoExcluirCozinhaInexistente(){
//        CozinhaNaoEncontradaException erroEsperado =
//                org.junit.jupiter.api.Assertions.assertThrows(CozinhaNaoEncontradaException.class, () ->{
//                    cadastroCozinha.excluir(100L);
//                });
//        assertThat(erroEsperado).isNotNull();
//    }
}
