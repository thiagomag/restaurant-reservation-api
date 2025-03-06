package br.com.postechfiap.restaurantreservationapi.utils.restaurante;

import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RestauranteHelperIT {

    @Autowired
    private RestauranteHelper restauranteHelper;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    @DisplayName("getRestauranteById - Restaurante encontrado")
    public void deveRetornarRestauranteQuandoExistir() {
        // Arrange
        Long restauranteId = 1L;

        // Act
        Restaurante restaurante = restauranteHelper.getRestauranteById(restauranteId);

        // Assert
        assertThat(restaurante).isNotNull();
        assertThat(restaurante.getId()).isEqualTo(restauranteId);
    }

    @Test
    @DisplayName("getRestauranteById - Restaurante não encontrado")
    public void deveLancarExcecaoQuandoRestauranteNaoExistir() {
        // Arrange
        Long restauranteIdInexistente = 999L; // ID que não existe

        // Act & Assert
        assertThatThrownBy(() -> restauranteHelper.getRestauranteById(restauranteIdInexistente))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado.");
    }

    @Test
    @DisplayName("validateRestauranteExists - Restaurante encontrado")
    public void deveRetornarRestauranteQuandoValidado() {
        // Arrange
        Long restauranteId = 1L;

        // Act
        Restaurante restaurante = restauranteHelper.validateRestauranteExists(restauranteId);

        // Assert
        assertThat(restaurante).isNotNull();
        assertThat(restaurante.getId()).isEqualTo(restauranteId);
    }

    @Test
    @DisplayName("validateRestauranteExists - Restaurante não encontrado")
    public void deveLancarExcecaoQuandoRestauranteNaoExistirNaValidacao() {
        // Arrange
        Long restauranteIdInexistente = 999L;

        // Act & Assert
        assertThatThrownBy(() -> restauranteHelper.validateRestauranteExists(restauranteIdInexistente))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado.");
    }

}
