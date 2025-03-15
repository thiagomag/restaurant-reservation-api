package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.DeletarRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarRestauranteUseCaseImpl implements DeletarRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;

    @Override
    public Void execute(Long restauranteId) {
        final var restaurante = restauranteRepository.findById(restauranteId);
        if (restaurante.isPresent()) {
            final var restauranteParaDeletar = restaurante.get();
            restauranteParaDeletar.delete();
            restauranteRepository.save(restauranteParaDeletar);
        } else {
            throw new RestauranteNotFoundException();
        }
        return null;
    }
}
