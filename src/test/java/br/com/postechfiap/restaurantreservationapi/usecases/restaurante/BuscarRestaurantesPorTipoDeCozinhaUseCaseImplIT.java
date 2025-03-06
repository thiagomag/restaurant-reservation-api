package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaTipoCozinhaRequest;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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
        RestauranteBuscaTipoCozinhaRequest request = new RestauranteBuscaTipoCozinhaRequest(TiposCozinhaEnum.ITALIANA);

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorTipoDeCozinhaUseCase.execute(request);

        // Assert
        assertThat(response).isNotEmpty(); // Verifica que pelo menos um restaurante foi encontrado
        assertThat(response.get(0).getTipoCozinha().getValue()).isEqualTo("Italiana");
    }

    @Test
    public void deveLancarErroQuandoTipoDeCozinhaForNuloOuVazio() {
        // Arrange
        RestauranteBuscaTipoCozinhaRequest requestNulo = new RestauranteBuscaTipoCozinhaRequest(null);

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorTipoDeCozinhaUseCase.execute(requestNulo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O tipo de cozinha não pode ser nulo.");
    }

    @Test
    public void deveLancarErroQuandoNenhumRestauranteForEncontrado() {
        // Arrange
        RestauranteBuscaTipoCozinhaRequest requestNaoEncontrado =
                new RestauranteBuscaTipoCozinhaRequest(TiposCozinhaEnum.CHINESA);

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorTipoDeCozinhaUseCase.execute(requestNaoEncontrado))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado.");
    }


}

