package br.com.postechfiap.restaurantreservationapi.exception.reserva;

public class ReservaJaAvaliadaException extends RuntimeException {

    public ReservaJaAvaliadaException(Long reservaId) {
        super("Reserva " + reservaId + " já foi avaliada.");
    }
}
