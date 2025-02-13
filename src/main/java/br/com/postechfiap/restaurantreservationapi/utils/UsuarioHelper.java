package br.com.postechfiap.restaurantreservationapi.utils;

import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsuarioHelper {

    private final UsuarioRepository usuarioRepository;

    public Usuario validateUsuarioExists(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(UsuarioNotFoundException::new);
    }
}