package br.com.postechfiap.restaurantreservationapi.exception.reserva;

import br.com.postechfiap.restaurantreservationapi.exception.ConflictException;

public class ReservaJaAvaliadaException extends ConflictException {

    public ReservaJaAvaliadaException(Long reservaId) {
        super("Reserva " + reservaId + " já foi avaliada.");
    }
}
