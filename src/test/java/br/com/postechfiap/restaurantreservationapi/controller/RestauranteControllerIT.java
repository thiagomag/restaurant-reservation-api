package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaLocalizacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaNomeRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaTipoCozinhaRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorLocalizacaoUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorNomeUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorTipoDeCozinhaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.CadastrarRestauranteUseCase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestauranteControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private CadastrarRestauranteUseCase cadastrarRestauranteUseCase;
    @Autowired
    private BuscarRestaurantesPorNomeUseCase buscarRestaurantesPorNomeUseCase;
    @Autowired
    private BuscarRestaurantesPorTipoDeCozinhaUseCase buscarRestaurantesPorTipoDeCozinhaUseCase;
    @Autowired
    private BuscarRestaurantesPorLocalizacaoUseCase buscarRestaurantesPorLocalizacaoUseCase;

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
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body("nome", equalTo("Restaurante Sabor"))
                .body("tipo_cozinha", equalTo("ITALIANA")) // Ajustado para corresponder ao JSON
                .body("endereco.logradouro", equalTo("Rua A"))
                .body("endereco.estado", equalTo("RJ")); // Adicionado para garantir que o estado seja salvo corretamente
    }

    @Test
    void deveBuscarRestaurantesPorNomeComSucesso() {
        // Criar um objeto de request válido (RestauranteBuscaNomeRequest)
        RestauranteBuscaNomeRequest buscaNomeRequest = RestauranteBuscaNomeRequest.builder()
                .nome("Sabor")
                .build();

        // Realizar o POST usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(buscaNomeRequest)
                .when()
                .post("/restaurante/findByName")
                .then()
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body("[0].nome", equalTo("Restaurante Sabor"))
                .body("[0].tipo_cozinha", equalTo("ITALIANA")) // Ajustado para o nome correto do campo
                .body("[0].endereco.logradouro", equalTo("Rua A"))
                .body("[0].endereco.bairro", equalTo("Centro"))
                .body("[0].endereco.cidade", equalTo("São Paulo"))
                .body("[0].endereco.estado", equalTo("SP"));
    }

    @Test
    void deveBuscarRestaurantesPorTipoDeCozinhaComSucesso() {
        // Criar um objeto de request válido (RestauranteBuscaTipoCozinhaRequest)
        RestauranteBuscaTipoCozinhaRequest buscaTipoCozinhaRequest = RestauranteBuscaTipoCozinhaRequest.builder()
                .tipoCozinha(TiposCozinhaEnum.ITALIANA)
                .build();

        // Realizar o POST usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(buscaTipoCozinhaRequest)
                .when()
                .post("/restaurante/findByTipoCozinha")
                .then()
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body("[0].nome", equalTo("Restaurante Sabor"))
                .body("[0].tipo_cozinha", equalTo("ITALIANA")) // Alterado para corresponder ao JSON retornado
                .body("[0].endereco.logradouro", equalTo("Rua A"))
                .body("[0].endereco.bairro", equalTo("Centro"))
                .body("[0].endereco.cidade", equalTo("São Paulo"))
                .body("[0].endereco.estado", equalTo("SP"));
    }

    @Test
    void deveBuscarRestaurantesPorLocalizacaoComSucesso() {
        // Criar um objeto de request válido (RestauranteBuscaLocalizacaoRequest)
        RestauranteBuscaLocalizacaoRequest buscaLocalizacaoRequest = RestauranteBuscaLocalizacaoRequest.builder()
                .cep("01000-000")
                .estado("SP")
                .cidade("São Paulo")
                .logradouro("Rua A")
                .build();

        // Realizar o POST usando Rest Assured e verificar a resposta
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(buscaLocalizacaoRequest)
                .when()
                .post("/restaurante/findByLocalizacao")
                .then()
                .statusCode(200)  // Verifica que o status retornado é 200 OK
                .body("[0].nome", equalTo("Restaurante Sabor"))
                .body("[0].tipo_cozinha", equalTo("ITALIANA")) // Alterado para corresponder ao JSON retornado
                .body("[0].endereco.logradouro", equalTo("Rua A")) // Alterado para validar corretamente o endereço
                .body("[0].endereco.numero", equalTo(100)); // Adicionado número para precisão na validação
    }

    @Test
    void deveRetornarErroQuandoBuscarRestaurantePorNomeComDadosInvalidos() {
        // Criar um objeto de request inválido (nome nulo)
        RestauranteBuscaNomeRequest buscaNomeRequest = RestauranteBuscaNomeRequest.builder()
                .nome(null)
                .build();

        // Realizar o POST e verificar o erro
        given()
                .contentType(ContentType.JSON)
                .body(buscaNomeRequest)
                .when()
                .post("/restaurante/findByName")
                .then()
                .statusCode(400)  // Verifica que o status retornado é 400 Bad Request
                .body("message[0]", containsString("O nome do restaurante é obrigatório")); // Mensagem corrigida
    }
}
