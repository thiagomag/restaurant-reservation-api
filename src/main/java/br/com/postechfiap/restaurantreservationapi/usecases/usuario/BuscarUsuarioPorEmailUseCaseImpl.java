package br.com.postechfiap.restaurantreservationapi.usecases.usuario;

import br.com.postechfiap.restaurantreservationapi.adapters.usuario.UsuarioResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioResponse;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.BuscarUsuarioPorEmailUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarUsuarioPorEmailUseCaseImpl implements BuscarUsuarioPorEmailUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioResponseAdapter usuarioResponseAdapter;

    @Override
    public UsuarioResponse execute(String email) {
        final var usuario =usuarioRepository.findByEmail(email);
        if (usuario.isEmpty()) {
            throw new UsuarioNotFoundException();
        }
        return usuarioResponseAdapter.adapt(usuario.get());
    }
}
