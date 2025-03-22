package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioRequest;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import br.com.postechfiap.restaurantreservationapi.utils.usuario.UsuarioTestUtils;
import io.restassured.RestAssured;
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
import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext
public class UsuarioControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(usuarioRequest)  // Passando o objeto no corpo da requisição
                .when()
                .post("/usuario")
                .then()
                .statusCode(201)
                .body("id", equalTo(4))
                .body("nome", equalTo("João"))
                .body("email", equalTo("teste@teste.com"))
                .body("telefone", equalTo("11999999999"));
    }

    @Test
    void deveRetornarErroQuandoNomeForNulo() {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        usuarioRequest.setNome(null);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(usuarioRequest)  // Passando o objeto no corpo da requisição
                .when()
                .post("/usuario")
                .then()
                .statusCode(400);
    }

    @Test
    void deveRetornarErroQuandoNomeForVazio() {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        usuarioRequest.setNome("");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(usuarioRequest)  // Passando o objeto no corpo da requisição
                .when()
                .post("/usuario")
                .then()
                .statusCode(400);
    }

    @Test
    void deveRetornarErroQuandoEmailForNulo() {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        usuarioRequest.setEmail(null);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(usuarioRequest)  // Passando o objeto no corpo da requisição
                .when()
                .post("/usuario")
                .then()
                .statusCode(400);
    }

    @Test
    void deveRetornarErroQuandoEmailForVazio() {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        usuarioRequest.setEmail("");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(usuarioRequest)  // Passando o objeto no corpo da requisição
                .when()
                .post("/usuario")
                .then()
                .statusCode(400);
    }

    @Test
    void deveBuscarUsuarioPorEmailComSucesso() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("email", "mariana@email.com")
                .when()
                .get("/usuario/findByEmail")
                .then()
                .statusCode(200)
                .body("id", equalTo(2))
                .body("nome", equalTo("Mariana Souza"))
                .body("email", equalTo("mariana@email.com"));
    }

    @Test
    void deveRetornarErroQuandoUsuarioNaoEncontrado() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("email", "ugabuga@email.com")
                .when()
                .get("/usuario/findByEmail")
                .then()
                .statusCode(404);
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        final var usuarioRequest = new UsuarioRequest();
        usuarioRequest.setEmail("novoemail@email.com");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(usuarioRequest)  // Passando o objeto no corpo da requisição
                .when()
                .put("/usuario/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("nome", equalTo("Carlos Silva"))
                .body("email", equalTo("novoemail@email.com"));
    }

    @Test
    void deveRetornarErroAoAtualizarUsuario() {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        usuarioRequest.setNome(null);
        usuarioRequest.setTelefone(null);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(usuarioRequest)  // Passando o objeto no corpo da requisição
                .when()
                .put("/usuario/5")
                .then()
                .statusCode(404);
    }

    @Test
    void deveDeletarUsuarioComSucesso() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/usuario/3")
                .then()
                .statusCode(204);
    }

    @Test
    void deveRetornarErroAoDeletarUsuario() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/usuario/5")
                .then()
                .statusCode(404);
    }
}
