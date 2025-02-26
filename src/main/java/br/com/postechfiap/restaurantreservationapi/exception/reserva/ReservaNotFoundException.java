package br.com.postechfiap.restaurantreservationapi.exception.reserva;

import br.com.postechfiap.restaurantreservationapi.exception.EntityNotFoundException;

public class ReservaNotFoundException extends EntityNotFoundException {

    public ReservaNotFoundException() {
        super("Reserva", "a");
    }
}
