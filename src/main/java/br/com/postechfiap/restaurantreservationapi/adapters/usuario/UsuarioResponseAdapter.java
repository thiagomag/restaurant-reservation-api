package br.com.postechfiap.restaurantreservationapi.adapters.usuario;

import br.com.postechfiap.restaurantreservationapi.adapters.AbstractAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class UsuarioResponseAdapter extends AbstractAdapter<Usuario, UsuarioResponse> {

    public UsuarioResponseAdapter(JsonUtils jsonUtils) {
        super(UsuarioResponse.class, jsonUtils);
    }
}
