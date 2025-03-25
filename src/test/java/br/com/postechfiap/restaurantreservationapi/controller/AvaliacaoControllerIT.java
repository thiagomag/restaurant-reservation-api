package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext
class AvaliacaoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    ReservaRepository reservaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @Test
    void deveCriarAvaliacaoComSucesso() {
        // Criar um objeto de request (AvaliacaoRequest)
        AvaliacaoRequest avaliacaoRequest = new AvaliacaoRequest();
        avaliacaoRequest.setReservaId(3L);
        avaliacaoRequest.setNota(5);
        avaliacaoRequest.setComentario("Ótima reserva!");

        // Realizar o POST usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(avaliacaoRequest)  // Passando o objeto no corpo da requisição
                .when()
                .post("/avaliar")
                .then()
                .statusCode(201)  // Verifica que o status retornado é 201 Created
                .body("reserva_id", equalTo(3))
                .body("nota", equalTo(5))
                .body("comentario", equalTo("Ótima reserva!"));
    }

    @Test
    void deveRetornarErroAoPassarRequestInvalido() {
        // Criar um objeto de request inválido (nota negativa)
        AvaliacaoRequest avaliacaoRequest = new AvaliacaoRequest();
        avaliacaoRequest.setReservaId(1L);
        avaliacaoRequest.setNota(-1);  // Valor inválido
        avaliacaoRequest.setComentario("Comentário inválido");

        // Realizar o POST e verificar o erro
        given()
                .contentType(ContentType.JSON)
                .body(avaliacaoRequest)
                .when()
                .post("/avaliar")
                .then()
                .statusCode(400)  // Verifica que o status retornado é 400 Bad Request
                .body("message[0]", containsString("A nota deve ser no mínimo 1."));  // Ajustar a mensagem para refletir o erro real
    }

    @Test
    void deveRetornarAvaliacoesPorUsuario() {
        // Realizar o GET e verificar a resposta
        given()
                .when()
                .get("/avaliar/findByUsuario?usuarioId=2")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    void deveRetornarErroAoBuscarAvaliacoesPorUsuarioInexistente() {
        // Realizar o GET e verificar a resposta
        given()
                .when()
                .get("/avaliar/findByUsuario?usuarioId=999")
                .then()
                .statusCode(404)
                .body("message[0]", containsString("Usuário não encontrado."));
    }

    @Test
    void deveRetornarAvaliacaoPorRestaurante() {
        // Realizar o GET e verificar a resposta
        given()
                .when()
                .get("/avaliar/findByRestaurante?restauranteId=2")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    void deveRetornarErroAoBuscarAvaliacoesPorRestauranteInexistente() {
        // Realizar o GET e verificar a resposta
        given()
                .when()
                .get("/avaliar/findByRestaurante?restauranteId=999")
                .then()
                .statusCode(404)
                .body("message[0]", containsString("Restaurante não encontrado."));
    }
}