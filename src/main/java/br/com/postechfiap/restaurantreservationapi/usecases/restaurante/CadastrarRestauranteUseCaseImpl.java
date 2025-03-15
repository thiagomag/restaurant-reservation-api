package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.restaurante.RestauranteAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.restaurante.RestauranteResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.endereco.EnderecoRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.CadastrarRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarRestauranteUseCaseImpl implements CadastrarRestauranteUseCase {

    private final EnderecoAdapter enderecoAdapter;
    private final EnderecoResponseAdapter enderecoResponseAdapter;
    private final EnderecoRepository enderecoRepository;
    private final RestauranteAdapter restauranteAdapter;
    private final RestauranteResponseAdapter restauranteResponseAdapter;
    private final RestauranteRepository restauranteRepository;

    @Override
    public RestauranteResponse execute(RestauranteRequest restauranteRequest) {
        final var restaurante = restauranteAdapter.adapt(restauranteRequest);
        final var endereco = enderecoAdapter.adapt(restauranteRequest.getEndereco());
        restaurante.setEndereco(endereco);

        enderecoResponseAdapter.adapt(enderecoRepository.save(endereco));

        return restauranteResponseAdapter.adapt(restauranteRepository.save(restaurante));
    }
}
