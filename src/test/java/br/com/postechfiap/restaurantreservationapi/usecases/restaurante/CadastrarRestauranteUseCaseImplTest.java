package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.restaurante.RestauranteAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.restaurante.RestauranteResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.CadastrarRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CadastrarRestauranteUseCaseImplTest {

    @Mock
    private EnderecoAdapter enderecoAdapter;
    @Mock
    private RestauranteAdapter restauranteAdapter;
    @Mock
    private RestauranteResponseAdapter restauranteResponseAdapter;
    @Mock
    private RestauranteRepository restauranteRepository;

    private CadastrarRestauranteUseCase cadastrarRestauranteUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cadastrarRestauranteUseCase = new CadastrarRestauranteUseCaseImpl(enderecoAdapter, restauranteAdapter,
                restauranteResponseAdapter, restauranteRepository);
    }

    @Test
    public void cadastraRestauranteReturnSuccessfully() {
        //given
        final var restauranteRequest = RestauranteTestUtils.buildRestauranteRequest();
        final var restaurante = RestauranteTestUtils.buildRestaurante();
        final var restauranteResponse = RestauranteTestUtils.buildRestauranteResponse();

        when(enderecoAdapter.adapt(restauranteRequest.getEndereco()))
                .thenReturn(restaurante.getEndereco());
        when(restauranteAdapter.adapt(restauranteRequest))
                .thenReturn(restaurante);
        when(restauranteRepository.save(restaurante))
                .thenReturn(restaurante);
        when(restauranteResponseAdapter.adapt(restaurante))
                .thenReturn(restauranteResponse);

        //when
        final var actual = cadastrarRestauranteUseCase.execute(restauranteRequest);

        //then
        assertNotNull(actual);
        assertEquals(restauranteResponse, actual);
        assertEquals(restauranteResponse.getId(), actual.getId());
        assertEquals(restauranteResponse.getNome(), actual.getNome());
        assertEquals(restauranteResponse.getEndereco(), actual.getEndereco());
        assertEquals(restauranteResponse.getTipoCozinha(), actual.getTipoCozinha());
        assertEquals(restauranteResponse.getHorarioFuncionamento(), actual.getHorarioFuncionamento());
        assertEquals(restauranteResponse.getCapacidade(), actual.getCapacidade());

        verify(enderecoAdapter, times(1)).adapt(restauranteRequest.getEndereco());
        verify(restauranteAdapter, times(1)).adapt(restauranteRequest);
        verify(restauranteRepository, times(1)).save(restaurante);
        verify(restauranteResponseAdapter, times(1)).adapt(restaurante);
    }
}
