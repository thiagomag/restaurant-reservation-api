package br.com.postechfiap.restaurantreservationapi.controller;

// Mock MVC


import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.exception.GlobalExceptionHandler;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliarReservaUseCase;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AvaliacaoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AvaliacaoController avaliacaoController;

    @Mock
    private AvaliarReservaUseCase avaliarReservaUseCase;

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




}
