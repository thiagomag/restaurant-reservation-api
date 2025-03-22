package br.com.postechfiap.restaurantreservationapi.utils.usuario;

import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioRequest;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Usuario;

public class UsuarioTestUtils {

    public static UsuarioRequest buildUsuarioRequest() {
        return UsuarioRequest.builder()
                .nome("João")
                .email("teste@teste.com")
                .telefone("11999999999")
                .build();
    }

    public static Usuario buildUsuario() {
        return Usuario.builder()
                .id(1L)
                .nome("João")
                .email("teste@teste.com")
                .telefone("11999999999")
                .build();
    }

    public static UsuarioResponse buildUsuarioResponse() {
        return UsuarioResponse.builder()
                .id(1L)
                .nome("João")
                .email("teste@teste.com")
                .telefone("11999999999")
                .build();
    }
}
