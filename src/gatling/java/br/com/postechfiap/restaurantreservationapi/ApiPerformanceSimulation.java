package br.com.postechfiap.restaurantreservationapi;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ApiPerformanceSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .header("Content-Type", "application/json");

    ActionBuilder adicinarUsuarioRequest = http("adicionar usuario")
            .post("/usuario")
            .body(StringBody(session -> String.format("""
                      {
                        "nome": "Fulano de Teste",
                        "email": "fulano%s@teste.com",
                        "telefone": "11999999999"
                      }
                    """, UUID.randomUUID())))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("usuarioId"))
            .check(jsonPath("$.email").saveAs("usuarioEmail"));

    ActionBuilder buscarUsuarioRequest = http("buscar usuario")
            .get(session -> "/usuario/findByEmail?email=" + session.getString("usuarioEmail"))
            .check(status().is(200));

    ActionBuilder deletarUsuarioRequest = http("deletar usuario")
            .delete("/usuario/#{usuarioId}")
            .check(status().is(204));

    ActionBuilder criarRestauranteRequest = http("criar restaurante")
            .post("/restaurante")
            .body(StringBody(session -> """
                    {
                      "nome": "Restaurante do Chef",
                      "endereco": {
                        "cep": "01000-000",
                        "estado": "SP",
                        "cidade": "São Paulo",
                        "bairro": "Centro",
                        "logradouro": "Rua das Flores",
                        "numero": 123
                      },
                      "tipo_cozinha": "ITALIANA",
                      "horario_funcionamento": "10:00 - 22:00",
                      "capacidade": 100
                    }
                    """))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("restauranteId"));

    ActionBuilder criarMesasRequest = http("Criar mesas")
            .post("/mesas")
            .body(StringBody(session -> """
                    {
                      "restaurante_id": %s,
                      "quantidade_mesas": 10
                    }
                    """.formatted(session.getString("restauranteId"))))
            .asJson()
            .check(status().is(201));

    ActionBuilder adicionarReservaRequest = http("Adicionar reserva")
            .post("/reserva")
            .body(StringBody(session -> """
                    {
                      "restaurante_id": %d,
                      "data_hora_reserva": "2024-03-15T19:30:00",
                      "numero_de_pessoas": 1,
                      "usuario_id": %d
                    }
                    """.formatted(session.getLong("restauranteId"), session.getLong("usuarioId"))))
            .asJson()
            .check(status().is(201))
            .check(jsonPath("$.reserva_id").saveAs("reservaId"));;

    ActionBuilder buscarReservaRequest = http("Buscar reserva")
            .get(session -> "/reserva/findByRestauranteId?restauranteId=" + session.getString("restauranteId"))
            .check(status().is(200));

    ActionBuilder deletarReservaRequest = http("Deletar reserva")
            .delete("/reserva/#{reservaId}")
            .check(status().is(204));

    ActionBuilder adicionarAvaliacao = http("Adicionar avaliação")
            .post("/avaliar")
            .body(StringBody(session -> """
                    {
                      "reserva_id": %s,
                      "nota": 5,
                      "comentario": "Excelente atendimento!"
                    }
                    """.formatted(session.getString("reservaId"))))
            .asJson()
            .check(status().is(201));


    ScenarioBuilder cenarioAdicionarUsuario = scenario("Adicionar usuário")
            .exec(adicinarUsuarioRequest);

    ScenarioBuilder cenarioBuscarUsuario = scenario("Buscar usuário")
            .exec(adicinarUsuarioRequest)
            .exec(buscarUsuarioRequest);

    ScenarioBuilder cenarioDeletarUsuario = scenario("Deletar usuário")
            .exec(adicinarUsuarioRequest)
            .exec(deletarUsuarioRequest);

    ScenarioBuilder cenarioCriarRestauranteComMesa = scenario("Criar restaurante com mesas")
            .exec(criarRestauranteRequest)
            .exec(criarMesasRequest);

    ScenarioBuilder cenarioAdicionarReserva = scenario("Adicionar reserva")
            .exec(criarRestauranteRequest)
            .exec(criarMesasRequest)
            .exec(adicinarUsuarioRequest)
            .exec(adicionarReservaRequest);

    ScenarioBuilder cenarioBuscarReserva = scenario("Buscar reserva")
            .exec(criarRestauranteRequest)
            .exec(criarMesasRequest)
            .exec(adicinarUsuarioRequest)
            .exec(adicionarReservaRequest)
            .exec(buscarReservaRequest);

    ScenarioBuilder cenarioDeletarReserva = scenario("Deletar reserva")
            .exec(criarRestauranteRequest)
            .exec(adicionarReservaRequest)
            .exec(deletarReservaRequest);

    ScenarioBuilder cenarioAdicionarAvaliacao = scenario("Adicionar avaliação")
            .exec(criarRestauranteRequest)
            .exec(criarMesasRequest)
            .exec(adicinarUsuarioRequest)
            .exec(adicionarReservaRequest)
            .exec(adicionarAvaliacao);

    {
        setUp(
                cenarioAdicionarUsuario.injectOpen(
                        rampUsersPerSec(1)
                                .to(3)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(3)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(3)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioBuscarUsuario.injectOpen(
                        rampUsersPerSec(1)
                                .to(3)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(3)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(3)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioDeletarUsuario.injectOpen(
                        rampUsersPerSec(1)
                                .to(3)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(3)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(3)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioCriarRestauranteComMesa.injectOpen(
                        rampUsersPerSec(1)
                                .to(3)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(3)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(3)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarReserva.injectOpen(
                        rampUsersPerSec(1)
                                .to(3)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(3)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(3)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioBuscarReserva.injectOpen(
                                rampUsersPerSec(1)
                                        .to(3)
                                        .during(Duration.ofSeconds(10)),
                                constantUsersPerSec(3)
                                        .during(Duration.ofSeconds(30)),
                                rampUsersPerSec(3)
                                        .to(1)
                                        .during(Duration.ofSeconds(10))),
                cenarioDeletarReserva.injectOpen(
                        rampUsersPerSec(1)
                                .to(3)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(3)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(3)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAdicionarAvaliacao.injectOpen(
                        rampUsersPerSec(1)
                                .to(3)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(3)
                                .during(Duration.ofSeconds(30)),
                        rampUsersPerSec(3)
                                .to(1)
                                .during(Duration.ofSeconds(10))))
                        .protocols(httpProtocol)
                        .assertions(
                                global().responseTime().max().lt(60),
                                global().failedRequests().count().is(0L));
    }
}

