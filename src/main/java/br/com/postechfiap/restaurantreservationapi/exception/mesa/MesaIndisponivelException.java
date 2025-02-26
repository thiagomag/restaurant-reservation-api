package br.com.postechfiap.restaurantreservationapi.exception.mesa;

public class MesaIndisponivelException extends RuntimeException {
    public MesaIndisponivelException(String message) {
        super(message);
    }
}
