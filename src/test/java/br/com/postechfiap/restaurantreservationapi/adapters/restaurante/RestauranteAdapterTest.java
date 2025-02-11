package br.com.postechfiap.restaurantreservationapi.adapters.restaurante;

import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestauranteAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var restauranteRequest = RestauranteTestUtils.buildRestauranteRequest();

        //when
        final var actual = new RestauranteAdapter(jsonUtils).adapt(restauranteRequest);

        //then
        assertEquals(restauranteRequest.getNome(), actual.getNome());
        assertEquals(restauranteRequest.getTipoCozinha(), actual.getTipoCozinha());
        assertEquals(restauranteRequest.getCapacidade(), actual.getCapacidade());
        assertEquals(restauranteRequest.getHorarioFuncionamento(), actual.getHorarioFuncionamento());

    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach2() {
        //given
        final var restauranteRequest = RestauranteTestUtils.buildRestauranteRequest();
        restauranteRequest.setNome("Novo Nome");
        final var restaurante = RestauranteTestUtils.buildRestaurante();

        //when
        final var actual = new RestauranteAdapter(jsonUtils).adapt(restauranteRequest, restaurante);

        //then
        assertEquals(restauranteRequest.getNome(), actual.getNome());
        assertEquals(restauranteRequest.getTipoCozinha(), actual.getTipoCozinha());
        assertEquals(restauranteRequest.getCapacidade(), actual.getCapacidade());
        assertEquals(restauranteRequest.getHorarioFuncionamento(), actual.getHorarioFuncionamento());
    }

}
