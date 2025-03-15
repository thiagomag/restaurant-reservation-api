package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BuscarRestaurantesPorTipoDeCozinhaUseCaseImplIT {

    @Autowired
    private BuscarRestaurantesPorTipoDeCozinhaUseCaseImpl buscarRestaurantesPorTipoDeCozinhaUseCase;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void deveBuscarRestaurantesPorTipoDeCozinhaComSucesso() {
        // Arrange
        final var tipoCozinha = "Italiana";

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorTipoDeCozinhaUseCase.execute(tipoCozinha);

        // Assert
        assertThat(response).isNotEmpty(); // Verifica que pelo menos um restaurante foi encontrado
        System.out.println(response);
        assertThat(response.getFirst().getTipoCozinha().getValue()).isEqualTo("Italiana");
    }

    @Test
    public void deveLancarErroQuandoTipoDeCozinhaForNuloOuVazio() {
        // Arrange
        final var tipoCozinha = "Qlqr coisa";

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorTipoDeCozinhaUseCase.execute(tipoCozinha))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O tipo de cozinha não pode ser nulo.");
    }

    @Test
    public void deveLancarErroQuandoNenhumRestauranteForEncontrado() {
        // Arrange
        final var tipoCozinha = "chinesa";

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorTipoDeCozinhaUseCase.execute(tipoCozinha))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado.");
    }


}

