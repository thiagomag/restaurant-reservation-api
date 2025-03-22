package br.com.postechfiap.restaurantreservationapi.usecases.usuario;

import br.com.postechfiap.restaurantreservationapi.adapters.usuario.UsuarioAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.usuario.UsuarioResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioRequest;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.CadastrarUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import br.com.postechfiap.restaurantreservationapi.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarUsuarioUseCaseImpl implements CadastrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAdapter usuarioAdapter;
    private final UsuarioResponseAdapter usuarioResponseAdapter;
    private final UsuarioValidator usuarioValidator;

    @Override
    public UsuarioResponse execute(UsuarioRequest entry) {
        usuarioValidator.validateDuplicatedEmail(entry.getEmail());
        final var usuario = usuarioRepository.save(usuarioAdapter.adapt(entry));
        return usuarioResponseAdapter.adapt(usuario);
    }
}
