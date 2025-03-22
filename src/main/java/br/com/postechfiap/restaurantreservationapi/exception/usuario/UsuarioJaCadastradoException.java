package br.com.postechfiap.restaurantreservationapi.exception.usuario;

import br.com.postechfiap.restaurantreservationapi.exception.ConflictException;

public class UsuarioJaCadastradoException extends ConflictException {

  public UsuarioJaCadastradoException() {
        super("Usuário já cadastrado");
    }
}
