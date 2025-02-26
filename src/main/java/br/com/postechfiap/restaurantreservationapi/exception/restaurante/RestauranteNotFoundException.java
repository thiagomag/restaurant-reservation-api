package br.com.postechfiap.restaurantreservationapi.exception.restaurante;

import br.com.postechfiap.restaurantreservationapi.exception.EntityNotFoundException;

public class RestauranteNotFoundException extends EntityNotFoundException {

    public RestauranteNotFoundException() {
        super("Restaurante", "o");
    }
}
