package br.com.postechfiap.restaurantreservationapi.interfaces.usuario;

import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;
import org.springframework.stereotype.Service;

@Service
public interface BuscarUsuarioPorEmailUseCase extends UseCase<String, UsuarioResponse> {
}
