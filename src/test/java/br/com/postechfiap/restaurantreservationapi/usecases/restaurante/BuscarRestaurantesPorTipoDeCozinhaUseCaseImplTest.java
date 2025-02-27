package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaNomeRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaTipoCozinhaRequest;
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
class BuscarRestaurantesPorTipoDeCozinhaUseCaseImplTest {

    @InjectMocks
    private BuscarRestaurantesPorTipoDeCozinhaUseCaseImpl buscarRestaurantesPorTipoDeCozinhaUseCase;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RestauranteValidator restauranteValidator;

    @Mock
    private Restaurante restaurante;

    @Mock
    private Endereco endereco;

    private RestauranteBuscaTipoCozinhaRequest request;

    @BeforeEach
    void setUp() {
        request = new RestauranteBuscaTipoCozinhaRequest(TiposCozinhaEnum.ITALIANA);
    }

    @Test
    void deveBuscarRestaurantesPorTipoDeCozinha() {

        // Arrange
        List<Restaurante> restaurantes = List.of(restaurante);

        doNothing().when(restauranteValidator).validateTipoCozinha(request.getTipoCozinha());

        when(restauranteRepository.findByTipoCozinha(request.getTipoCozinha()))
                .thenReturn(restaurantes);

        when(restaurante.getId()).thenReturn(1L);
        when(restaurante.getNome()).thenReturn("Restaurante Italiano");
        when(restaurante.getTipoCozinha()).thenReturn(TiposCozinhaEnum.ITALIANA);
        when(restaurante.getHorarioFuncionamento()).thenReturn("10:00 - 22:00");
        when(restaurante.getCapacidade()).thenReturn(100);
        when(restaurante.getEndereco()).thenReturn(endereco);
        when(endereco.getNumero()).thenReturn(14);

        // Mockando o Endereco
        when(restaurante.getEndereco()).thenReturn(endereco);
        when(endereco.getNumero()).thenReturn(123);

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorTipoDeCozinhaUseCase.execute(request);

        // Assert
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getId()).isEqualTo(1L);
        assertThat(response.get(0).getNome()).isEqualTo("Restaurante Italiano");
        assertThat(response.get(0).getTipoCozinha()).isEqualTo(TiposCozinhaEnum.ITALIANA);
        assertThat(response.get(0).getHorarioFuncionamento()).isEqualTo("10:00 - 22:00");
        assertThat(response.get(0).getCapacidade()).isEqualTo(100);

        verify(restauranteValidator).validateTipoCozinha(request.getTipoCozinha());
        verify(restauranteRepository).findByTipoCozinha(request.getTipoCozinha());
    }

    @Test
    void deveLancarExcecaoQuandoNenhumRestauranteForEncontrado() {

        // Arrange
        doNothing().when(restauranteValidator).validateTipoCozinha(request.getTipoCozinha());

        when(restauranteRepository.findByTipoCozinha(request.getTipoCozinha()))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorTipoDeCozinhaUseCase.execute(request))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado.");

        verify(restauranteValidator).validateTipoCozinha(request.getTipoCozinha());
        verify(restauranteRepository).findByTipoCozinha(request.getTipoCozinha());
    }

    @Test
    void deveLancarExcecaoQuandoTipoDeCozinhaForNulo() {
        // Arrange
        RestauranteBuscaTipoCozinhaRequest nullRequest = new RestauranteBuscaTipoCozinhaRequest(null);

        doThrow(new IllegalArgumentException("O tipo de cozinha não pode ser nulo."))
                .when(restauranteValidator).validateTipoCozinha(nullRequest.getTipoCozinha());

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorTipoDeCozinhaUseCase.execute(nullRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O tipo de cozinha não pode ser nulo.");

        verify(restauranteValidator).validateTipoCozinha(nullRequest.getTipoCozinha());
        verifyNoInteractions(restauranteRepository);
    }

}
