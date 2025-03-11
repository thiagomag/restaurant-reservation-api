package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaLocalizacaoRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
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
public class BuscarRestaurantesPorLocalizacaoUseCaseImplIT {

    @Autowired
    private BuscarRestaurantesPorLocalizacaoUseCaseImpl buscarRestaurantesPorLocalizacaoUseCase;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void deveBuscarRestaurantesComSucesso() {
        // Arrange
        RestauranteBuscaLocalizacaoRequest request = new RestauranteBuscaLocalizacaoRequest
                ("01000-000", "SP", "Sao Paulo", "Rua A");

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorLocalizacaoUseCase.execute(request);

        // Assert
        assertThat(response).isNotEmpty();
        assertThat(response.get(0).getNome()).isEqualTo("Restaurante Sabor");
    }

    @Test
    public void deveBuscarRestaurantesComSucessoApenasCep() {
        // Arrange
        RestauranteBuscaLocalizacaoRequest request = RestauranteBuscaLocalizacaoRequest.builder()
                .cep("01000-000").build();

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorLocalizacaoUseCase.execute(request);

        // Assert
        assertThat(response).isNotEmpty();
        assertThat(response.get(0).getNome()).isEqualTo("Restaurante Sabor");
    }




    @Test
    public void deveLancarErroQuandoRestauranteNaoForEncontrado() {
        // Arrange
        RestauranteBuscaLocalizacaoRequest request = new RestauranteBuscaLocalizacaoRequest
                ("Logradouro Inexistente", "00000-000", "Cidade", "Estado");

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorLocalizacaoUseCase.execute(request))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado.");
    }
}