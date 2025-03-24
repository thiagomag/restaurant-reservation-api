package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.gerenciamento.ReservaAtualizarDataHoraRequest;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.AtualizarDataHoraReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.CancelarReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservarMesaUseCase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ReservaControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ReservarMesaUseCase reservarMesaUseCase;
    @Autowired
    private CancelarReservaUseCase cancelarReservaUseCase;
    @Autowired
    private AtualizarDataHoraReservaUseCase atualizarDataHoraReservaUseCase;

    LocalDateTime dateTime;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        dateTime = LocalDateTime.of(2025,5,1,19,0,0);

    }

    @Test
    void deveCriarReservaComSucesso() {
        // Criar um objeto de request válido (ReservaRequest)
        ReservaRequest reservaRequest = ReservaRequest.builder()
                .restauranteId(1L)
                .usuarioId(1L)
                .dataHoraReserva(dateTime)  // Assumindo que dateTime é um valor válido
                .numeroDePessoas(2)
                .build();

        // Realizar o POST usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservaRequest)  // Passando o objeto no corpo da requisição
                .when()
                .post("/reserva")
                .then()
                .statusCode(201)  // Verifica que o status retornado é 201 Created
                .body("reserva_id", notNullValue())  // Alterado para "reserva_id"
                .body("reserva_id", greaterThan(0)) // Verifica que o valor do "reserva_id" é maior que 0
                .body("restaurante_name", equalTo("Restaurante Sabor"))  // Nome do restaurante
                .body("data_hora_reserva", equalTo("2025-05-01T19:00:00"))  // Data e hora da reserva
                .body("numero_de_pessoas", equalTo(2));  // Número de pessoas
    }

    @Test
    void deveRetornarErroQuandoDadosInvalidosNaReserva() {
        // Criar um objeto de request inválido (dataHoraReserva nula)
        ReservaRequest reservaRequest = ReservaRequest.builder()
                .restauranteId(1L)
                .usuarioId(1L)
                .dataHoraReserva(null)
                .numeroDePessoas(2)
                .build();

        // Realizar o POST e verificar o erro
        given()
                .contentType(ContentType.JSON)
                .body(reservaRequest)
                .when()
                .post("/reserva")
                .then()
                .statusCode(400)  // Verifica que o status retornado é 400 Bad Request
                .body("message[0]", containsString("A data e hora da reserva não podem ser nulas."));

    }

    @Test
    void deveDeletarReservaComSucesso() {
        // Supondo que o id da reserva a ser deletada seja 1L
        Long reservaId = 3L;

        // Realizar o DELETE usando Rest Assured e verificar a resposta
        given()
                .when()
                .delete("/reserva/{id}", reservaId)
                .then()
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body(equalTo("Reserva com ID 3 foi deletada com sucesso."));
    }

    @Test
    void deveRetornarErroQuandoDeletarReservaNaoExistente() {
        // Realizar o DELETE usando Rest Assured para uma reserva que não existe
        Long reservaIdInexistente = 999L;

        // Realizar o DELETE e verificar o erro
        given()
                .when()
                .delete("/reserva/{id}", reservaIdInexistente)
                .then()
                .statusCode(404)  // Verifica que o status retornado é 404 Not Found
                .body("message[0]", containsString("Reserva não encontrada"));
    }

    @Test
    void deveAtualizarReservaComSucesso() {
        // Criar um objeto de request válido para atualizar reserva
        LocalDateTime dateTimeAtualizada = LocalDateTime.of(2025,5,1,19,0,0);

        ReservaAtualizarDataHoraRequest atualizarRequest = ReservaAtualizarDataHoraRequest.builder()
                        .reservaId(2L).dataAlteracao(dateTimeAtualizada).build();

        // Realizar o PUT usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(atualizarRequest)
                .when()
                .put("/reserva")
                .then()
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body("reserva_id", equalTo(2));

    }

    @Test
    void deveRetornarErroQuandoAtualizarReservaComDadosInvalidos() {
        // Criar um objeto de request inválido para atualização (dataHoraReserva nula)
        ReservaAtualizarDataHoraRequest atualizarRequest = ReservaAtualizarDataHoraRequest.builder()
                .reservaId(1L).dataAlteracao(null).build();

        // Realizar o PUT e verificar o erro
        given()
                .contentType(ContentType.JSON)
                .body(atualizarRequest)
                .when()
                .put("/reserva")
                .then()
                .statusCode(400)  // Verifica que o status retornado é 400 Bad Request
                .body("message[0]", containsString("Data e hora não pode ser nulo."));
    }

    @Test
    void deveRetornarListaReservaPorRestaurante() {
        // Supondo que o id do restaurante seja 1L
        Long restauranteId = 1L;

        // Realizar o GET usando Rest Assured e verificar a resposta
        given()
                .when()
                .get("/reserva/findByRestauranteId?restauranteId={restauranteId}", restauranteId)
                .then()
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body("size()", greaterThan(0));  // Verifica que a lista de reservas não está vazia
    }

    @Test
    void deveRetornarErroQuandoBuscarReservaPorRestauranteInexistente() {
        // Supondo que o id do restaurante seja 999L (inexistente)
        Long restauranteIdInexistente = 999L;

        // Realizar o GET e verificar o erro
        given()
                .when()
                .get("/reserva/findByRestauranteId?restauranteId={restauranteId}", restauranteIdInexistente)
                .then()
                .statusCode(404)  // Verifica que o status retornado é 404 Not Found
                .body("message[0]", containsString("Restaurante não encontrado"));
    }
}
