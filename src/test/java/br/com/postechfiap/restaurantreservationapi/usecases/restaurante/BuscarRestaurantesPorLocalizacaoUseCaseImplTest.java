package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaLocalizacaoRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.validator.RestauranteValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarRestaurantesPorLocalizacaoUseCaseImplTest {

    @InjectMocks
    private BuscarRestaurantesPorLocalizacaoUseCaseImpl buscarRestaurantesPorLocalizacaoUseCase;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RestauranteValidator restauranteValidator;

    @Mock
    private Restaurante restaurante;

    @Mock
    private Endereco endereco;

    @Mock
    private EnderecoResponse enderecoResponse;

    private RestauranteBuscaLocalizacaoRequest request;

    @BeforeEach
    void setUp() {
        request = new RestauranteBuscaLocalizacaoRequest
                ("Logradouro", "12345-678", "Cidade", "Estado");
    }

    @Test
    void deveRetornarRestaurantesQuandoEncontrados() {
        // Arrange

        // Lista simulada com 1 restaurante
        List<Restaurante> restaurantes = List.of(restaurante);

        when(restauranteRepository.findByLocalizacao(request.getLogradouro(), request.getCep(), request.getCidade(), request.getEstado()))
                .thenReturn(restaurantes);

        // Mockando os comportamentos do restaurante
        when(restaurante.getId()).thenReturn(1L);
        when(restaurante.getNome()).thenReturn("Nome do Restaurante");
        when(restaurante.getTipoCozinha()).thenReturn(TiposCozinhaEnum.ITALIANA);
        when(restaurante.getHorarioFuncionamento()).thenReturn("10:00 - 22:00");
        when(restaurante.getCapacidade()).thenReturn(100);

        // Mockando o Endereco
        when(restaurante.getEndereco()).thenReturn(endereco);
        when(endereco.getNumero()).thenReturn(123);

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorLocalizacaoUseCase.execute(request);

        // Assert
        assertThat(response).isNotEmpty();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getNome()).isEqualTo("Nome do Restaurante");
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getTipoCozinha()).isEqualTo(TiposCozinhaEnum.ITALIANA);
        assertThat(response.get(0).getHorarioFuncionamento()).isEqualTo("10:00 - 22:00");
        assertThat(response.get(0).getCapacidade()).isEqualTo(100);

        // Verificação de chamada do repositório
        verify(restauranteRepository).findByLocalizacao(
                request.getLogradouro(), request.getCep(), request.getCidade(), request.getEstado());
    }


    @Test
    void deveLancarExcecaoQuandoNenhumRestauranteForEncontrado() {
        // Arrange
        when(restauranteRepository.findByLocalizacao(request.getLogradouro(), request.getCep(), request.getCidade(), request.getEstado()))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorLocalizacaoUseCase.execute(request))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado.");

        // Verificar se o repositório foi chamado
        verify(restauranteRepository).findByLocalizacao(request.getLogradouro(), request.getCep(), request.getCidade(), request.getEstado());
    }

    @Test
    void deveChamarRepositorioComOsParametrosCorretos() {
        // Arrange
        List<Restaurante> restaurantes = List.of(restaurante);

        when(restauranteRepository.findByLocalizacao(
                request.getLogradouro(),
                request.getCep(),
                request.getCidade(),
                request.getEstado()))
                .thenReturn(restaurantes);

        when(endereco.getId()).thenReturn(1L); // Stub para getId()
        when(restaurante.getEndereco()).thenReturn(endereco);

        // Act
        buscarRestaurantesPorLocalizacaoUseCase.execute(request);

        // Assert
        verify(restauranteRepository).findByLocalizacao(request.getLogradouro(), request.getCep(), request.getCidade(), request.getEstado());
    }

}



