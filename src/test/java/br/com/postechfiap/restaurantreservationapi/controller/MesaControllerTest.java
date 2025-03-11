package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponseList;
import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.exception.GlobalExceptionHandler;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.CadastrarMesaUseCase;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class MesaControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MesaController mesaController;

    @Mock
    private CadastrarMesaUseCase cadastrarMesaUseCase;

    AutoCloseable openMocks;

    @Mock
    Restaurante restaurante;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mesaController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Se necessário
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    void deveCadastrarMesaComSucesso() throws Exception {
        // Criar objetos de teste
        final var request = MesaRequest.builder().restauranteId(1L).quantidadeMesas(2).build();
        var mesa1 = MesaResponse.builder().id("001-001").numeroMesa(1).restauranteId(1L).build();
        var mesa2 = MesaResponse.builder().id("001-002").numeroMesa(2).restauranteId(1L).build();

        final var response = MesaResponseList.builder().mesas(List.of(mesa1, mesa2)).build();

        // Configurar comportamento do mock
        when(cadastrarMesaUseCase.execute(any(MesaRequest.class)))
                .thenReturn(response);

        // Converter o objeto para JSON
        final var requestJson = new ObjectMapper().writeValueAsString(request);

        // Simular a requisição POST
        mockMvc.perform(post("/mesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                // Ajustar para acessar o id dentro de cada item da lista de mesas
                .andExpect(jsonPath("$.mesas[0].id").value("001-001"))
                .andExpect(jsonPath("$.mesas[0].numeroMesa").value(1))
                .andExpect(jsonPath("$.mesas[0].restauranteId").value(1))
                .andExpect(jsonPath("$.mesas[1].id").value("001-002"))
                .andExpect(jsonPath("$.mesas[1].numeroMesa").value(2))
                .andExpect(jsonPath("$.mesas[1].restauranteId").value(1));

        // Verificar se o caso de uso foi chamado corretamente
        verify(cadastrarMesaUseCase, times(1)).execute(any(MesaRequest.class));
    }

    @Test
    @DisplayName("Deve falhar ao cadastrar mesa com capacidade negativa")
    void deveFalharAoCadastrarMesaComCapacidadeNegativa() throws Exception {
        final var request = MesaRequest.builder().restauranteId(1L).quantidadeMesas(-2).build();
        final var requestJson = new ObjectMapper().writeValueAsString(request);

        // Simular o comportamento de falha na validação
        mockMvc.perform(post("/mesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

}
