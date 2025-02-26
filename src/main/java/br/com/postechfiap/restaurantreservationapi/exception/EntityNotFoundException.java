package br.com.postechfiap.restaurantreservationapi.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, String genero) {
        super(entityName + " não encontrad" + genero + ".");
    }
}
