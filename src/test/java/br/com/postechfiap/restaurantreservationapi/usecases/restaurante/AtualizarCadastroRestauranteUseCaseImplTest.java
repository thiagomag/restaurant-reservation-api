package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.restaurante.RestauranteAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.restaurante.RestauranteResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.AtualizarCadastroRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.utils.restaurante.RestauranteTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
public class AtualizarCadastroRestauranteUseCaseImplTest {

    @Mock
    private RestauranteRepository restauranteRepository;
    @Mock
    private RestauranteAdapter restauranteAdapter;
    @Mock
    private EnderecoAdapter enderecoAdapter;
    @Mock
    private RestauranteResponseAdapter restauranteResponseAdapter;

    private AtualizarCadastroRestauranteUseCase atualizarCadastroRestauranteUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        atualizarCadastroRestauranteUseCase = new AtualizarCadastroRestauranteUseCaseImpl(restauranteRepository, restauranteAdapter,
                enderecoAdapter, restauranteResponseAdapter);
    }

    @Test
    public void atualizaRestauranteReturnSuccessfully() {
        //given
        final var restauranteRequest = RestauranteRequest.builder().id(1L).nome("Novo Nome").build();
        final var restaurante = RestauranteTestUtils.buildRestaurante();
        final var restauranteResponse = RestauranteTestUtils.buildRestauranteResponse();

        when(restauranteRepository.findById(restauranteRequest.getId()))
                .thenReturn(Optional.of(restaurante));
        when(restauranteAdapter.adapt(restauranteRequest, restaurante))
                .thenAnswer(invocation -> {
                    final var request = invocation.getArgument(0, RestauranteRequest.class);
                    final var entity = invocation.getArgument(1, Restaurante.class);
                    entity.setNome(request.getNome());
                    return entity;
                });
        when(restauranteRepository.save(any(Restaurante.class)))
                .thenAnswer(invocation -> {
                        final var persisted = (Restaurante) invocation.getArgument(0);

                        assertEquals(restauranteRequest.getNome(), persisted.getNome());
                        return persisted;
                });

        when(restauranteResponseAdapter.adapt(any(Restaurante.class)))
                .thenReturn(restauranteResponse);

        //when
        final var actual = atualizarCadastroRestauranteUseCase.execute(restauranteRequest);

        //then
        assertNotNull(actual);
    }

    @Test
    public void atualizaRestauranteReturnSuccessfully2() {
        //given
        final var endecoRequest = EnderecoRequest.builder().cep("11650-022")
                .estado("RJ")
                .cidade("Rio de Janeiro")
                .bairro("Novo Bairro")
                .logradouro("Nova Rua")
                .numero(100)
                .build();
        final var endereco = Endereco.builder()
                .id(3L)
                .estado("RJ")
                .cidade("Rio de Janeiro")
                .bairro("Novo Bairro")
                .logradouro("Nova Rua")
                .numero(50)
                .build();

        final var restauranteRequest = RestauranteRequest.builder().id(1L).nome("Novo Nome").endereco(endecoRequest).build();
        final var restaurante = RestauranteTestUtils.buildRestaurante();
        final var restauranteResponse = RestauranteTestUtils.buildRestauranteResponse();

        when(restauranteRepository.findById(restauranteRequest.getId()))
                .thenReturn(Optional.of(restaurante));
        when(restauranteAdapter.adapt(restauranteRequest, restaurante))
                .thenAnswer(invocation -> {
                    final var request = invocation.getArgument(0, RestauranteRequest.class);
                    final var entity = invocation.getArgument(1, Restaurante.class);
                    entity.setNome(request.getNome());
                    return entity;
                });
        when(enderecoAdapter.adapt(endecoRequest))
                .thenAnswer(invocation -> {
                    final var request = invocation.getArgument(0, EnderecoRequest.class);
                    endereco.setBairro(request.getBairro());
                    endereco.setCidade(request.getCidade());
                    endereco.setEstado(request.getEstado());
                    endereco.setLogradouro(request.getLogradouro());
                    endereco.setNumero(request.getNumero());
                    return endereco;
                });
        when(restauranteRepository.save(any(Restaurante.class)))
                .thenAnswer(invocation -> {
                    final var persisted = (Restaurante) invocation.getArgument(0);

                    assertEquals(restauranteRequest.getNome(), persisted.getNome());
                    return persisted;
                });
        when(restauranteResponseAdapter.adapt(restaurante))
                .thenReturn(restauranteResponse);

        //when
        final var actual = atualizarCadastroRestauranteUseCase.execute(restauranteRequest);

        //then
        assertNotNull(actual);
    }

    @Test
    public void atualizaRestauranteReturnNotFoundException() {
        //given
        final var restauranteRequest = RestauranteRequest.builder().id(999L).nome("Novo Nome").build();

        when(restauranteRepository.findById(restauranteRequest.getId()))
                .thenReturn(Optional.empty());

        //when
        try {
            atualizarCadastroRestauranteUseCase.execute(restauranteRequest);
        } catch (Exception e) {
            //then
            assertEquals("Restaurante não encontrado.", e.getMessage());
        }
    }
}
