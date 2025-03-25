package br.com.postechfiap.restaurantreservationapi.controller;

// Mock MVC


import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.exception.GlobalExceptionHandler;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliarReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.ListarAvaliacoesPorRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.ListarAvaliacoesPorUsuarioUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class AvaliacaoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AvaliacaoController avaliacaoController;

    @Mock
    private AvaliarReservaUseCase avaliarReservaUseCase;
    @Mock
    private ListarAvaliacoesPorUsuarioUseCase listarAvaliacoesPorUsuarioUseCase;
    @Mock
    private ListarAvaliacoesPorRestauranteUseCase listarAvaliacoesPorRestauranteUseCase;


    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(avaliacaoController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Se necessário
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    void deveAvaliarReservaComSucesso() throws Exception {
        // Criar objetos de teste
        final var request = AvaliacaoRequest.builder().nota(5).comentario("Excelente!").reservaId(1L).build();
        final var response = AvaliacaoResponse.builder().id(1L).nota(5).comentario("Excelente!").reservaId(1L).build();

        // Configurar comportamento do mock
        when(avaliarReservaUseCase.execute(any(AvaliacaoRequest.class)))
                .thenReturn(response);

        // Converter o objeto para JSON
        final var requestJson = new ObjectMapper().writeValueAsString(request);

        // Simular a requisição POST
        mockMvc.perform(post("/avaliar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nota").value(5))
                .andExpect(jsonPath("$.comentario").value("Excelente!"));

        // Verificar se o caso de uso foi chamado corretamente
        verify(avaliarReservaUseCase, times(1)).execute(any(AvaliacaoRequest.class));
    }

    @Test
    @DisplayName("Deve falhar ao avaliar reserva com nota errada")
    void deveFalharAoAvaliarReservaComNotaErrada() throws Exception {
        final var request = AvaliacaoRequest.builder().nota(0).comentario("Excelente!").reservaId(1L).build();
        final var requestJson = new ObjectMapper().writeValueAsString(request);

        // Simular o comportamento de falha na validação
        mockMvc.perform(post("/avaliar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve falhar ao avaliar reserva sem ID da reserva")
    void deveFalharAoAvaliarReservaSemIdReserva() throws Exception {
        final var request = AvaliacaoRequest.builder().nota(5).comentario("Excelente!").reservaId(null).build();
        final var requestJson = new ObjectMapper().writeValueAsString(request);

        // Simular o comportamento de falha na validação
        mockMvc.perform(post("/avaliar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Deve falhar ao avaliar reserva sem nota")
    void deveFalharAoAvaliarReservaSemNota() throws Exception {
        final var request = AvaliacaoRequest.builder().nota(null).comentario("Excelente!").reservaId(1L).build();
        final var requestJson = new ObjectMapper().writeValueAsString(request);

        // Simular o comportamento de falha na validação
        mockMvc.perform(post("/avaliar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar avaliações por usuário")
    void deveRetornarAvaliacoesPorUsuario() throws Exception {
        final var response = AvaliacaoResponse.builder().id(1L).nota(5).comentario("Excelente!").reservaId(1L).build();

        when(listarAvaliacoesPorUsuarioUseCase.execute(any()))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/avaliar/findByUsuario?usuarioId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve falhar ao buscar avaliações por usuário inexistente")
    void deveFalharAoBuscarAvaliacoesPorUsuarioInexistente() throws Exception {
        doThrow(new UsuarioNotFoundException())
                .when(listarAvaliacoesPorUsuarioUseCase).execute(any());

        mockMvc.perform(get("/avaliar/findByUsuario?usuarioId=999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar avaliações por restaurante")
    void deveRetornarAvaliacoesPorRestaurante() throws Exception {
        final var response = AvaliacaoResponse.builder().id(1L).nota(5).comentario("Excelente!").reservaId(1L).build();

        when(listarAvaliacoesPorRestauranteUseCase.execute(any()))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/avaliar/findByRestaurante?restauranteId=1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve falhar ao buscar avaliações por restaurante inexistente")
    void deveFalharAoBuscarAvaliacoesPorRestauranteInexistente() throws Exception {
        doThrow(new ReservaNotFoundException())
                .when(listarAvaliacoesPorRestauranteUseCase).execute(any());

        mockMvc.perform(get("/avaliar/findByRestaurante?restauranteId=999"))
                .andExpect(status().isNotFound());
    }



}
