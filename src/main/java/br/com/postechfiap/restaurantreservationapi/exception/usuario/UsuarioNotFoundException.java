package br.com.postechfiap.restaurantreservationapi.exception.usuario;

import br.com.postechfiap.restaurantreservationapi.exception.EntityNotFoundException;

public class UsuarioNotFoundException extends EntityNotFoundException {

    public UsuarioNotFoundException() {
        super("Usuário", "o");
    }
}
