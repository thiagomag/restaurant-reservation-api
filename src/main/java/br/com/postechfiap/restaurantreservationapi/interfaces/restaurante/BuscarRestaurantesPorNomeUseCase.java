package br.com.postechfiap.restaurantreservationapi.interfaces.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;

import java.util.List;

public interface BuscarRestaurantesPorNomeUseCase extends UseCase<String, List<RestauranteResponse>> {
}
