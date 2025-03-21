package br.com.postechfiap.restaurantreservationapi.bdd;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponseList;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.utils.restaurante.RestauranteTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

public class AvaliacaoControllerSteps {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private ResponseEntity<AvaliacaoResponse> response;
    private AvaliacaoRequest request;

    @Dado("que existe uma reserva válida")
    public void queExisteUmaReservaValida() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Criar restaurante antes de criar a reserva
        String restauranteUrl = "http://localhost:8080/restaurante";

        RestauranteRequest novoRestaurante = RestauranteTestUtils.buildRestauranteRequest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> restauranteEntity = new HttpEntity<>(objectMapper.writeValueAsString(novoRestaurante), headers);
        ResponseEntity<RestauranteResponse> restauranteResponse = restTemplate.exchange(restauranteUrl, HttpMethod.POST, restauranteEntity, RestauranteResponse.class);

        Assertions.assertEquals(HttpStatus.CREATED, restauranteResponse.getStatusCode());

        // Criar mesa para restaurante antes de criar a reserva
        String mesasUrl = "http://localhost:8080/mesas";

        MesaRequest mesaRequest = new MesaRequest();
        mesaRequest.setRestauranteId(Objects.requireNonNull(restauranteResponse.getBody()).getId());
        mesaRequest.setQuantidadeMesas(100);

        HttpEntity<String> mesaEntity = new HttpEntity<>(objectMapper.writeValueAsString(mesaRequest), headers);
        ResponseEntity<MesaResponseList> mesaResponse = restTemplate.exchange(mesasUrl, HttpMethod.POST, mesaEntity, MesaResponseList.class);

        Assertions.assertEquals(HttpStatus.CREATED, mesaResponse.getStatusCode());

        // Criar reserva com restaurante válido
        String reservaUrl = "http://localhost:8080/reserva";

        ReservaRequest novaReserva = new ReservaRequest();
        novaReserva.setRestauranteId(restauranteResponse.getBody().getId()); // Usando o ID do restaurante criado
        novaReserva.setNumeroDePessoas(1);
        novaReserva.setUsuarioId(1L);
        novaReserva.setDataHoraReserva(LocalDateTime.now().plusDays(1)); // Data futura

        HttpEntity<String> reservaEntity = new HttpEntity<>(objectMapper.writeValueAsString(novaReserva), headers);
        ResponseEntity<ReservaResponse> reservaResponse = restTemplate.exchange(reservaUrl, HttpMethod.POST, reservaEntity, ReservaResponse.class);

        Assertions.assertEquals(HttpStatus.CREATED, reservaResponse.getStatusCode());

        // Criar a requisição de avaliação com a reserva criada
        request = new AvaliacaoRequest();
        request.setReservaId(Objects.requireNonNull(reservaResponse.getBody()).getReservaId());
        request.setNota(5);
        request.setComentario("Ótima experiência!");
    }

    @Quando("eu envio a requisição de avaliação")
    public void euEnvioARequisicaoDeAvaliacao() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/avaliar";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);
        response = restTemplate.exchange(url, HttpMethod.POST, entity, AvaliacaoResponse.class);
    }

    @Entao("o sistema deve retornar status {int}")
    public void oSistemaDeveRetornarStatus(int statusCode) {
        Assertions.assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    @E("a resposta deve conter os dados da avaliação")
    public void aRespostaDeveConterOsDadosDaAvaliacao() {
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(request.getReservaId(), response.getBody().getReservaId());
        Assertions.assertEquals(request.getNota(), response.getBody().getNota());
        Assertions.assertEquals(request.getComentario(), response.getBody().getComentario());
    }
}

