package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaNomeRequest;
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
        RestauranteBuscaNomeRequest request = new RestauranteBuscaNomeRequest("Sabor");

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorNomeUseCase.execute(request);

        // Assert
        assertThat(response).isNotEmpty();
        assertThat(response.get(0).getNome()).isEqualTo("Restaurante Sabor");
    }

    @Test
    public void deveLancarErroQuandoNomeForInvalido() {
        // Arrange
        RestauranteBuscaNomeRequest requestInvalido = new RestauranteBuscaNomeRequest("R");

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorNomeUseCase.execute(requestInvalido))
                .isInstanceOf(IllegalArgumentException.class) // Supondo que a validação gere uma IllegalArgumentException
                .hasMessageContaining("O nome do restaurante deve ter pelo menos 3 caracteres");
    }

    @Test
    public void deveLancarErroQuandoInputForVazioOuNulo() {
        // Arrange
        RestauranteBuscaNomeRequest requestInexistente = new RestauranteBuscaNomeRequest("");

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorNomeUseCase.execute(requestInexistente))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O nome do restaurante não pode ser vazio ou nulo");
    }

    @Test
    public void deveLancarErroQuandoRestauranteNaoExistir() {
        // Arrange
        RestauranteBuscaNomeRequest requestInexistente = new RestauranteBuscaNomeRequest("Restaurante Saboroso");

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorNomeUseCase.execute(requestInexistente))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado.");
    }




}
