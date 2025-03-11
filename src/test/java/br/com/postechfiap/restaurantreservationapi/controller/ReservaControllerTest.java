package br.com.postechfiap.restaurantreservationapi.controller;


import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.gerenciamento.ReservaAtualizarDataHoraRequest;
import br.com.postechfiap.restaurantreservationapi.exception.GlobalExceptionHandler;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.AtualizarDataHoraReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.CancelarReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservarMesaUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDateTime;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
class ReservaControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ReservaController reservaController;

    @Mock
    private ReservarMesaUseCase reservarMesaUseCase;

    @Mock
    private CancelarReservaUseCase cancelarReservaUseCase;

    @Mock
    private AtualizarDataHoraReservaUseCase atualizarDataHoraReservaUseCase;

    AutoCloseable openMocks;

    LocalDateTime dateTime;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);


        mockMvc = MockMvcBuilders.standaloneSetup(reservaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();

        dateTime = LocalDateTime.of(2025, 3, 1, 19, 0, 0);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

    }

    @Test
    @DisplayName("Deve dar certo ao tentarreservar mesa")
    void deveReservarMesaComSucesso() throws Exception {

        final var request = ReservaRequest.builder()
                .restauranteId(1L)
                .usuarioId(1L)
                .dataHoraReserva(dateTime)
                .numeroDePessoas(2).build();
        final var requestJson = objectMapper.writeValueAsString(request);

        // Simular o comportamento de falha na validação
        mockMvc.perform(post("/reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve falhar ao reservar mesa com dados inválidos")
    void deveFalharAoReservarMesaComDadosInvalidos() throws Exception {

        final var request = ReservaRequest.builder()
                .restauranteId(1L)
                .usuarioId(1L)
                .dataHoraReserva(dateTime)
                .numeroDePessoas(0).build();
        final var requestJson = objectMapper.writeValueAsString(request);

        // Simular o comportamento de falha na validação
        mockMvc.perform(post("/reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    void deveCancelarReservaComSucesso() throws Exception {
        // Configurar o comportamento do mock
        when(cancelarReservaUseCase.execute(1L))
                .thenReturn("Reserva cancelada com sucesso");

        // Simular a requisição DELETE
        mockMvc.perform(delete("/reserva/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Reserva cancelada com sucesso"));

        // Verificar se o caso de uso foi chamado corretamente
        verify(cancelarReservaUseCase, times(1)).execute(1L);
    }

    @Test
    void deveAtualizarDataHoraReservaComSucesso() throws Exception {

        // Criar objetos de teste
        final var request = ReservaAtualizarDataHoraRequest.builder()
                .reservaId(1L).dataAlteracao(dateTime).build();

        var reservaAtualizada = ReservaResponse.builder()
                .reservaId(1L)
                .restauranteName("Restaurante Italiano")
                .mesas(null)
                .dataHoraReserva(dateTime)
                .numeroDePessoas(2)
                .build();

        // Configurar comportamento do mock
        when(atualizarDataHoraReservaUseCase.execute(any(ReservaAtualizarDataHoraRequest.class)))
                .thenReturn(reservaAtualizada);

        // Converter o objeto para JSON
        final var requestJson = objectMapper.writeValueAsString(request);

        // Simular a requisição PUT
        mockMvc.perform(put("/reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reserva_id").value(1));


        // Verificar se o caso de uso foi chamado corretamente
        verify(atualizarDataHoraReservaUseCase, times(1)).execute(any(ReservaAtualizarDataHoraRequest.class));
    }
}
