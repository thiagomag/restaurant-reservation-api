package br.com.postechfiap.restaurantreservationapi.interfaces.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;

public interface CadastrarRestauranteUseCase extends UseCase<RestauranteRequest, RestauranteResponse> {
}
