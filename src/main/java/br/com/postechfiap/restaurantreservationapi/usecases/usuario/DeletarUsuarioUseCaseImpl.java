package br.com.postechfiap.restaurantreservationapi.usecases.usuario;

import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.DeletarUsuarioUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeletarUsuarioUseCaseImpl implements DeletarUsuarioUseCase {

    @Override
    public Void execute(Long entry) {
        return null;
    }
}
