package br.com.postechfiap.restaurantreservationapi.validator;

import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioJaCadastradoException;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public Usuario validateUsuarioExists(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(UsuarioNotFoundException::new);
    }

    public void validateDuplicatedEmail(String email) {
        final var usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            throw new UsuarioJaCadastradoException();
        }
    }
}