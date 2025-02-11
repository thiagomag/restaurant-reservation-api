package br.com.postechfiap.restaurantreservationapi.adapters.restaurante;

import br.com.postechfiap.restaurantreservationapi.adapters.AbstractAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class RestauranteAdapter extends AbstractAdapter<RestauranteRequest, Restaurante> {

    public RestauranteAdapter(JsonUtils jsonUtils) {
        super(Restaurante.class, jsonUtils);
    }
}
