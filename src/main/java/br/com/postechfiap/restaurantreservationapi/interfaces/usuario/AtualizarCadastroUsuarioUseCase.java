package br.com.postechfiap.restaurantreservationapi.interfaces.usuario;

import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioRequest;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;

public interface AtualizarCadastroUsuarioUseCase extends UseCase<UsuarioRequest, UsuarioResponse> {
}
