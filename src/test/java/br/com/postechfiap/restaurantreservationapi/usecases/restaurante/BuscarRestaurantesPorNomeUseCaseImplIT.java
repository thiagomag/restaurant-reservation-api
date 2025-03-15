package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BuscarRestaurantesPorNomeUseCaseImplIT {

    @Autowired
    private BuscarRestaurantesPorNomeUseCaseImpl buscarRestaurantesPorNomeUseCase;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void deveBuscarRestaurantesComSucesso() {
        // Arrange
        final var nome = "Sabor";

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorNomeUseCase.execute(nome);

        // Assert
        assertThat(response).isNotEmpty();
        assertThat(response.getFirst().getNome()).isEqualTo("Restaurante Sabor");
    }

    @Test
    public void deveLancarErroQuandoRestauranteNaoExistir() {
        // Arrange
        final var nome ="Restaurante Saboroso";

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorNomeUseCase.execute(nome))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado.");
    }
}
