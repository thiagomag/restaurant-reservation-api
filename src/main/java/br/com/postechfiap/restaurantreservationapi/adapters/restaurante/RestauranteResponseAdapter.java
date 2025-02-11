package br.com.postechfiap.restaurantreservationapi.adapters.restaurante;

import br.com.postechfiap.restaurantreservationapi.adapters.AbstractAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class RestauranteResponseAdapter extends AbstractAdapter<Restaurante, RestauranteResponse> {


    public RestauranteResponseAdapter(JsonUtils jsonUtils) {
        super(RestauranteResponse.class, jsonUtils);
    }
}
