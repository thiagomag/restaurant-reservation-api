package br.com.postechfiap.restaurantreservationapi.usecases.usuario;

import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioRequest;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.AtualizarCadastroUsuarioUseCase;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioPorEmailUseCaseImpl implements AtualizarCadastroUsuarioUseCase {

    @Override
    public UsuarioResponse execute(UsuarioRequest entry) {
        return null;
    }
}
