package br.com.postechfiap.restaurantreservationapi.utils.restaurante;

import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RestauranteHelperTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private RestauranteHelper restauranteHelper;

    @BeforeEach
    public void setup() {}

    @Test
    void testGetRestauranteById_restauranteExiste() {
        // Arrange
        Long restauranteId = 1L;
        Restaurante restaurante = new Restaurante();
        restaurante.setId(restauranteId);

        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restaurante));

        // Act
        Restaurante result = restauranteHelper.getRestauranteById(restauranteId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(restauranteId);
    }

    @Test
    void testGetRestauranteById_restauranteNaoExiste() {
        // Arrange
        Long restauranteId = 1L;

        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> restauranteHelper.getRestauranteById(restauranteId))
                .isInstanceOf(RestauranteNotFoundException.class);

        //assertThatThrownBy(() -> reservaHelper.getReservaByHoraMarcada(restauranteId, inicio, fim))
        //        .isInstanceOf(ReservaNotFoundException.class)
        //        .hasMessage("Reserva não encontrada.");


    }

    @Test
    void testValidateRestauranteExists_restauranteExiste() {
        // Arrange
        Long restauranteId = 1L;
        Restaurante restaurante = new Restaurante();
        restaurante.setId(restauranteId);

        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restaurante));

        // Act
        Restaurante result = restauranteHelper.validateRestauranteExists(restauranteId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(restauranteId);
    }

    @Test
    void testValidateRestauranteExists_restauranteNaoExiste() {
        // Arrange
        Long restauranteId = 1L;

        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> restauranteHelper.validateRestauranteExists(restauranteId))
                .isInstanceOf(RestauranteNotFoundException.class);
    }
}
