package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaLocalizacaoRequest;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.GlobalExceptionHandler;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorLocalizacaoUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorNomeUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorTipoDeCozinhaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.CadastrarRestauranteUseCase;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class RestauranteControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RestauranteController restauranteController;

    @Mock
    private CadastrarRestauranteUseCase cadastrarRestauranteUseCase;

    @Mock
    private BuscarRestaurantesPorNomeUseCase buscarRestaurantesPorNomeUseCase;

    @Mock
    private BuscarRestaurantesPorTipoDeCozinhaUseCase buscarRestaurantesPorTipoDeCozinhaUseCase;

    @Mock
    private BuscarRestaurantesPorLocalizacaoUseCase buscarRestaurantesPorLocalizacaoUseCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    void deveCadastrarRestauranteComSucesso() throws Exception {
        // Criar objetos de teste
        final var request = RestauranteRequest.builder().nome("Restaurante Exemplo").build();
        final var response = RestauranteResponse.builder().id(1L).nome("Restaurante Exemplo").build();

        // Configurar comportamento do mock
        when(cadastrarRestauranteUseCase.execute(any(RestauranteRequest.class)))
                .thenReturn(response);

        // Converter o objeto para JSON
        final var requestJson = new ObjectMapper().writeValueAsString(request);

        // Simular a requisição POST
        mockMvc.perform(post("/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

        // Verificar se o caso de uso foi chamado corretamente
        verify(cadastrarRestauranteUseCase, times(1)).execute(any(RestauranteRequest.class));
    }

    @Test
    void deveBuscarRestaurantePorNomeComSucesso() throws Exception {
        // Criar objetos de teste
        final var response = List.of(new RestauranteResponse(1L, "Restaurante Exemplo",
                null, TiposCozinhaEnum.AMAZONICA,"11:00 - 22:00",10));

        // Configurar comportamento do mock
        when(buscarRestaurantesPorNomeUseCase.execute(anyString()))
                .thenReturn(response);

        // Simular a requisição POST
        mockMvc.perform(get("/restaurante/findByName?name=Restaurante Exemplo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Restaurante Exemplo"));

        verify(buscarRestaurantesPorNomeUseCase, times(1)).execute(anyString());
    }
    @Test
    void deveBuscarRestaurantePorTipoCozinhaComSucesso() throws Exception {
        final var response = List.of(new RestauranteResponse(2L, "Pizzaria Roma",
                null, TiposCozinhaEnum.ITALIANA,"11:00 - 22:00",10));

        when(buscarRestaurantesPorTipoDeCozinhaUseCase.execute(anyString()))
                .thenReturn(response);

        mockMvc.perform(get("/restaurante/findByTipoCozinha?tipoCozinha=Italiana")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].nome").value("Pizzaria Roma"));

        verify(buscarRestaurantesPorTipoDeCozinhaUseCase, times(1)).execute(anyString());
    }

    @Test
    void deveBuscarRestaurantePorLocalizacaoComSucesso() throws Exception {
        final var request = new RestauranteBuscaLocalizacaoRequest(null,null,"São Paulo",null);
        final var response = List.of(new RestauranteResponse(3L, "Churrascaria Paulista",
                null, TiposCozinhaEnum.ITALIANA,"11:00 - 22:00",10));

        when(buscarRestaurantesPorLocalizacaoUseCase.execute(any(RestauranteBuscaLocalizacaoRequest.class)))
                .thenReturn(response);

        final var requestJson = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(post("/restaurante/findByLocalizacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3L))
                .andExpect(jsonPath("$[0].nome").value("Churrascaria Paulista"));

        verify(buscarRestaurantesPorLocalizacaoUseCase, times(1))
                .execute(any(RestauranteBuscaLocalizacaoRequest.class));
    }

    @Test
    @DisplayName("Deve falhar ao cadastrar restaurante sem nome")
    void deveFalharAoCadastrarRestauranteSemNome() throws Exception {
        final var request = RestauranteRequest.builder().nome("").build();
        final var requestJson = new ObjectMapper().writeValueAsString(request);

        // Configurar comportamento do mock para o use case de cadastro
        doThrow(new IllegalArgumentException("O nome do restaurante não pode ser vazio ou nulo"))
                .when(cadastrarRestauranteUseCase).execute(any(RestauranteRequest.class));

        mockMvc.perform(post("/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Deve falhar ao buscar restaurante sem nome")
    void deveFalharAoBuscarRestauranteSemNome() throws Exception {
        mockMvc.perform(get("/restaurante/findByName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Deve falhar ao buscar restaurante por nome quando nenhum for encontrado")
    void deveFalharAoBuscarRestaurantePorNomeQuandoNenhumForEncontrado() throws Exception {
        // Configurar comportamento do mock
        doThrow(new ReservaNotFoundException())
                .when(buscarRestaurantesPorNomeUseCase).execute(anyString());

        mockMvc.perform(get("/restaurante/findByName?name=Inexistente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Deve falhar ao buscar restaurante por tipo de cozinha quando nenhum for encontrado")
    void deveFalharAoBuscarRestaurantePorTipoDeCozinhaQuandoNenhumForEncontrado() throws Exception {
        // Configurar comportamento do mock para o use case de busca por tipo de cozinha
        doThrow(new RestauranteNotFoundException())
                .when(buscarRestaurantesPorTipoDeCozinhaUseCase).execute(anyString());

        mockMvc.perform(get("/restaurante/findByTipoCozinha?tipoCozinha=qlqr")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve falhar ao buscar restaurante por localizacao quando nenhum for encontrado")
    void deveFalharAoBuscarRestaurantePorLocalizacaoQuandoNenhumForEncontrado() throws Exception {
        final var request = new RestauranteBuscaLocalizacaoRequest(null, null, "São Paulo", null);

        // Configurar comportamento do mock para o use case de busca por localizacao
        doThrow(new RestauranteNotFoundException())
                .when(buscarRestaurantesPorLocalizacaoUseCase).execute(any(RestauranteBuscaLocalizacaoRequest.class));

        final var requestJson = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(post("/restaurante/findByLocalizacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }






}