package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
@Transactional
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

    private TiposCozinhaEnum tipoCozinhaEnum;

    private static final String tipoCozinha = "Italiana";

    @BeforeEach
    void setUp() {
        tipoCozinhaEnum = TiposCozinhaEnum.findBy(tipoCozinha);
    }

    @Test
    void deveBuscarRestaurantesPorTipoDeCozinha() {

        // Arrange
        List<Restaurante> restaurantes = List.of(restaurante);

        doNothing().when(restauranteValidator).validateTipoCozinha(tipoCozinhaEnum);

        when(restauranteRepository.findByTipoCozinha(tipoCozinhaEnum))
                .thenReturn(restaurantes);

        lenient().when(restaurante.getId()).thenReturn(1L);
        lenient().when(restaurante.getNome()).thenReturn("Restaurante Italiano");
        lenient().when(restaurante.getTipoCozinha()).thenReturn(TiposCozinhaEnum.ITALIANA);
        lenient().when(restaurante.getHorarioFuncionamento()).thenReturn("10:00 - 22:00");
        lenient().when(restaurante.getCapacidade()).thenReturn(100);
        lenient().when(restaurante.getEndereco()).thenReturn(endereco);
        lenient().when(endereco.getNumero()).thenReturn(14);

        // Mockando o Endereco
        lenient().when(restaurante.getEndereco()).thenReturn(endereco);
        lenient().when(endereco.getNumero()).thenReturn(123);

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorTipoDeCozinhaUseCase.execute(tipoCozinha);

        RestauranteResponse restauranteResponse = response.getFirst();

        // Assert
        assertThat(response).hasSize(1);
        assertThat(restauranteResponse.getId()).isEqualTo(1L);
        assertThat(restauranteResponse.getTipoCozinha()).isEqualTo(TiposCozinhaEnum.ITALIANA);
        assertThat(restauranteResponse.getHorarioFuncionamento()).isEqualTo("10:00 - 22:00");
        assertThat(restauranteResponse.getCapacidade()).isEqualTo(100);

        verify(restauranteValidator).validateTipoCozinha(tipoCozinhaEnum);
        verify(restauranteRepository).findByTipoCozinha(tipoCozinhaEnum);
    }

    @Test
    void deveLancarExcecaoQuandoNenhumRestauranteForEncontrado() {

        // Arrange
        doNothing().when(restauranteValidator).validateTipoCozinha(tipoCozinhaEnum);

        when(restauranteRepository.findByTipoCozinha(tipoCozinhaEnum))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorTipoDeCozinhaUseCase.execute(tipoCozinha))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado.");

        verify(restauranteValidator).validateTipoCozinha(tipoCozinhaEnum);
        verify(restauranteRepository).findByTipoCozinha(tipoCozinhaEnum);
    }

    @Test
    void deveLancarExcecaoQuandoTipoDeCozinhaForNulo() {
        // Arrange
        final var tipoCozinha = "Qlqr coisa";
        tipoCozinhaEnum = TiposCozinhaEnum.findBy(tipoCozinha);

        doThrow(new IllegalArgumentException("O tipo de cozinha não pode ser nulo."))
                .when(restauranteValidator).validateTipoCozinha(tipoCozinhaEnum);

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorTipoDeCozinhaUseCase.execute(tipoCozinha))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O tipo de cozinha não pode ser nulo.");

        verify(restauranteValidator).validateTipoCozinha(tipoCozinhaEnum);
        verifyNoInteractions(restauranteRepository);
    }

}
