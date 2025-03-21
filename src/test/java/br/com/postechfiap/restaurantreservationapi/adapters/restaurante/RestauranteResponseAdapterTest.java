package br.com.postechfiap.restaurantreservationapi.adapters.restaurante;

import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import br.com.postechfiap.restaurantreservationapi.utils.restaurante.RestauranteTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestauranteResponseAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        // given
        final var restaurante = RestauranteTestUtils.buildRestaurante();

        // when
        final var actual = new RestauranteResponseAdapter(jsonUtils)
                .adapt(restaurante);

        // then
        assertEquals(restaurante.getId(), actual.getId());
        assertEquals(restaurante.getNome(), actual.getNome());
        assertEquals(restaurante.getHorarioFuncionamento(), actual.getHorarioFuncionamento());
        assertEquals(restaurante.getCapacidade(), actual.getCapacidade());
        assertEquals(restaurante.getTipoCozinha(), actual.getTipoCozinha());
        assertEquals(restaurante.getEndereco().getId(), actual.getEndereco().getId());
        assertEquals(restaurante.getEndereco().getLogradouro(), actual.getEndereco().getLogradouro());
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach2() {
        // given
        final var restaurante = RestauranteTestUtils.buildRestaurante();
        final var restauranteResponse = RestauranteTestUtils.buildRestauranteResponse();


        // when
        final var actual = new RestauranteResponseAdapter(jsonUtils)
                .adapt(restaurante, restauranteResponse);

        // then
        assertEquals(restaurante.getId(), actual.getId());
        assertEquals(restaurante.getNome(), actual.getNome());
        assertEquals(restaurante.getHorarioFuncionamento(), actual.getHorarioFuncionamento());
        assertEquals(restaurante.getCapacidade(), actual.getCapacidade());
        assertEquals(restaurante.getTipoCozinha(), actual.getTipoCozinha());
        assertEquals(restaurante.getEndereco().getId(), actual.getEndereco().getId());
        assertEquals(restaurante.getEndereco().getLogradouro(), actual.getEndereco().getLogradouro());
    }
}
