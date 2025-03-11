package br.com.postechfiap.restaurantreservationapi.controller;


import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.CadastrarMesaUseCase;
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


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class MesaControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private CadastrarMesaUseCase cadastrarMesaUseCase;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void deveCadastrarMesaComSucesso() {
        // Criar um objeto de request válido (MesaRequest)
        MesaRequest mesaRequest = MesaRequest.builder().restauranteId(1L).quantidadeMesas(4).build();

        // Realizar o POST usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(mesaRequest)  // Passando o objeto no corpo da requisição
                .when()
                .post("/mesas")
                .then()
                .statusCode(201)  // Verifica que o status retornado é 201 Created
                .body("mesas[0].numeroMesa", equalTo(3))
                .body("mesas[0].restauranteId", equalTo(1))
                .body("mesas[1].numeroMesa", equalTo(4))
                .body("mesas[1].restauranteId", equalTo(1))
                .body("mesas[2].numeroMesa", equalTo(5))
                .body("mesas[2].restauranteId", equalTo(1))
                .body("mesas[3].numeroMesa", equalTo(6))
                .body("mesas[3].restauranteId", equalTo(1));
    }

    @Test
    void deveRetornarErroQuandoDadosInvalido() {
        // Criar um objeto de request inválido (capacidade negativa)
        MesaRequest mesaRequest = MesaRequest.builder().restauranteId(1L).quantidadeMesas(-1).build();

        // Realizar o POST e verificar o erro
        given()
                .contentType(ContentType.JSON)
                .body(mesaRequest)
                .when()
                .post("/mesas")
                .then()
                .statusCode(400)  // Verifica que o status retornado é 400 Bad Request
                .body("message[0]", containsString("A quantidade de mesas não pode ser negativa"));  // Ajustar a mensagem conforme o erro real
    }
}
