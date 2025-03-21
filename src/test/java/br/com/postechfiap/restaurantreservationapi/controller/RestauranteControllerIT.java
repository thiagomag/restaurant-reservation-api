package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext
class RestauranteControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void deveCadastrarRestauranteComSucesso() {
        // Criar um objeto de request válido (RestauranteRequest)
        Endereco endereco = Endereco.builder().cep("11650-022")
                .estado("RJ")
                .cidade("Rio de Janeiro")
                .bairro("Bairro das Flores")
                .logradouro("Rua A")
                .numero(100)
                .build();

        EnderecoRequest enderecoRequest = EnderecoRequest.builder()
                .cep(endereco.getCep())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .bairro(endereco.getBairro())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero()).build();


        RestauranteRequest restauranteRequest = RestauranteRequest.builder()
                .nome("Restaurante Sabor")
                .tipoCozinha(TiposCozinhaEnum.ITALIANA)
                .endereco(enderecoRequest)
                .horarioFuncionamento("11:00 - 23:00")
                .capacidade(50)
                .build();

        // Realizar o POST usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restauranteRequest)
                .when()
                .post("/restaurante")
                .then()
                .statusCode(201)  // Verifica que o status retornado é 200 OK
                .body("nome", equalTo("Restaurante Sabor"))
                .body("tipo_cozinha", equalTo("ITALIANA")) // Ajustado para corresponder ao JSON
                .body("endereco.logradouro", equalTo("Rua A"))
                .body("endereco.estado", equalTo("RJ")); // Adicionado para garantir que o estado seja salvo corretamente
    }

    @Test
    void deveBuscarRestaurantesPorNomeComSucesso() {
        // Realizar o GET usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/restaurante/findByName?name=Restaurante Sabor")
                .then()
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body("[0].nome", equalTo("Restaurante Sabor"))
                .body("[0].tipo_cozinha", equalTo("ITALIANA")) // Ajustado para o nome correto do campo
                .body("[0].endereco.logradouro", equalTo("Rua A"))
                .body("[0].endereco.bairro", equalTo("Centro"))
                .body("[0].endereco.cidade", equalTo("Sao Paulo"))
                .body("[0].endereco.estado", equalTo("SP"));
    }

    @Test
    void deveBuscarRestaurantesPorTipoDeCozinhaComSucesso() {
        // Realizar o GET usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/restaurante/findByTipoCozinha?tipoCozinha=Italiana")
                .then()
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body("[0].nome", equalTo("Restaurante Sabor"))
                .body("[0].tipo_cozinha", equalTo("ITALIANA")) // Alterado para corresponder ao JSON retornado
                .body("[0].endereco.logradouro", equalTo("Rua A"))
                .body("[0].endereco.bairro", equalTo("Centro"))
                .body("[0].endereco.cidade", equalTo("Sao Paulo"))
                .body("[0].endereco.estado", equalTo("SP"));
    }

    @Test
    void deveBuscarRestaurantesPorLocalizacaoComSucesso() {

        // Realizar o POST usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/restaurante/findByLocalizacao?cep=01000-000&estado=SP&cidade=Sao Paulo&logradouro=Rua A")
                .then()
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body("[0].nome", equalTo("Restaurante Sabor"))
                .body("[0].tipo_cozinha", equalTo("ITALIANA")) // Alterado para corresponder ao JSON retornado
                .body("[0].endereco.logradouro", equalTo("Rua A")) // Alterado para validar corretamente o endereço
                .body("[0].endereco.numero", equalTo(100)); // Adicionado número para precisão na validação
    }

    @Test
    void deveRetornarErroQuandoBuscarRestaurantePorNomeComDadosInvalidos() {

        // Realizar o POST e verificar o erro
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/restaurante/findByName")
                .then()
                .statusCode(500)  // Verifica que o status retornado é 500 Internal Server Error
                .body("message[0]", containsString("Required request parameter 'name' for method parameter type String is not present")); // Mensagem corrigida
    }

    @Test
    void deveAtualizarRestauranteComSucesso() {
        // Criar um objeto de request válido (RestauranteRequest)

        RestauranteRequest restauranteRequest = RestauranteRequest.builder()
                .nome("Novo Nome")
                .build();

        // Realizar o PUT usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restauranteRequest)
                .when()
                .put("/restaurante/3")
                .then()
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body("nome", equalTo("Novo Nome"))
                .body("tipo_cozinha", equalTo("STEAKHOUSE")) // Ajustado para corresponder ao JSON
                .body("endereco.logradouro", equalTo("Rua C"))
                .body("endereco.estado", equalTo("MG")); // Adicionado para garantir que o estado seja salvo corretamente
    }

    @Test
    void deveRetornarErroAoAtualizarRestaurante() {
        // Criar um objeto de request válido (RestauranteRequest)

        RestauranteRequest restauranteRequest = RestauranteRequest.builder()
                .nome("Novo Nome")
                .build();

        // Realizar o PUT usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restauranteRequest)
                .when()
                .put("/restaurante/4")
                .then()
                .statusCode(404)
                .body("message[0]", containsString("Restaurante não encontrado."));
    }

    @Test
    void deveDeletarRestauranteComSucesso() {
        // Realizar o PUT usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/restaurante/2")
                .then()
                .statusCode(200);  // Verifica que o status retornado é 200 OK
    }

    @Test
    void deveRetornarErroAoDeletarRestaurante() {
        // Realizar o PUT usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/restaurante/4")
                .then()
                .statusCode(404)
                .body("message[0]", containsString("Restaurante não encontrado."));
    }
}
