package br.com.postechfiap.restaurantreservationapi.usecases;

import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.restaurate.RestauranteAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.restaurate.RestauranteResponseAdapter;
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
    private final RestauranteAdapter restauranteAdapter;
    private final RestauranteResponseAdapter restauranteResponseAdapter;
    private final EnderecoRepository enderecoRepository;
    private final RestauranteRepository restauranteRepository;

    @Override
    public RestauranteResponse execute(RestauranteRequest restauranteRequest) {
        final var endereco = enderecoRepository.save(enderecoAdapter.adapt(restauranteRequest.getEndereco()));
        final var restaurante = restauranteAdapter.adapt(restauranteRequest);
        restaurante.setEndereco(endereco);
        return restauranteResponseAdapter.adapt(restauranteRepository.save(restaurante));
    }
}
