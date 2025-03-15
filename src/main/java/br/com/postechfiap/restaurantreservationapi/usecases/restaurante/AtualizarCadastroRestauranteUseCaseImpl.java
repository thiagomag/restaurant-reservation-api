package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.restaurante.RestauranteAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.restaurante.RestauranteResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.AtualizarCadastroRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarCadastroRestauranteUseCaseImpl implements AtualizarCadastroRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;
    private final RestauranteAdapter restauranteAdapter;
    private final EnderecoAdapter enderecoAdapter;
    private final RestauranteResponseAdapter restauranteResponseAdapter;

    @Override
    public RestauranteResponse execute(RestauranteRequest restauranteRequest) {
        final var resturanteInDb = restauranteRepository.findById(restauranteRequest.getId());
        if (resturanteInDb.isPresent()) {
            final var restaurante = restauranteAdapter.adapt(restauranteRequest, resturanteInDb.get());
            if (restauranteRequest.getEndereco() != null) {
                final var endereco = enderecoAdapter.adapt(restauranteRequest.getEndereco());
                restaurante.setEndereco(endereco);
            }
            final var updatedRestaurante = restauranteRepository.save(restaurante);
            return restauranteResponseAdapter.adapt(updatedRestaurante);
        }
        throw new RestauranteNotFoundException();
    }
}
