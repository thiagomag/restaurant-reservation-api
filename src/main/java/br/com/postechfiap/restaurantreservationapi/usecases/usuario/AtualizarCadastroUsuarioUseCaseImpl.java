package br.com.postechfiap.restaurantreservationapi.usecases.usuario;

import br.com.postechfiap.restaurantreservationapi.adapters.usuario.UsuarioAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.usuario.UsuarioResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioRequest;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioResponse;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.AtualizarCadastroUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarCadastroUsuarioUseCaseImpl implements AtualizarCadastroUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAdapter usuarioAdapter;
    private final UsuarioResponseAdapter usuarioResponseAdapter;

    @Override
    public UsuarioResponse execute(UsuarioRequest entry) {
        final var usuario = usuarioRepository.findById(entry.getId())
                .orElseThrow(UsuarioNotFoundException::new);
        final var usuarioToBeSaved = usuarioAdapter.adapt(entry, usuario);
        final var usuarioSaved = usuarioRepository.save(usuarioToBeSaved);
        return usuarioResponseAdapter.adapt(usuarioSaved);
    }
}
