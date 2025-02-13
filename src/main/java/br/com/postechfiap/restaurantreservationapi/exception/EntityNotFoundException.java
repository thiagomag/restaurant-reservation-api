package br.com.postechfiap.restaurantreservationapi.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName) {
        super(entityName + " não encontrado.");
    }
}
