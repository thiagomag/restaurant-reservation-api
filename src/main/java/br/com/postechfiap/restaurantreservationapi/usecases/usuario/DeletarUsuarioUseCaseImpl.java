package br.com.postechfiap.restaurantreservationapi.usecases.usuario;

import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.DeletarUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarUsuarioUseCaseImpl implements DeletarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Void execute(Long entry) {
        final var usuario = usuarioRepository.findById(entry);
        if (usuario.isPresent()) {
            final var usuarioDeletado = usuario.get();
            usuarioDeletado.delete();
            usuarioRepository.save(usuarioDeletado);
            return null;
        } else {
            throw new UsuarioNotFoundException();
        }
    }
}
