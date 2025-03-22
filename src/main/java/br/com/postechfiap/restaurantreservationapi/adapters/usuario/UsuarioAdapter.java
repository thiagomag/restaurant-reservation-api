package br.com.postechfiap.restaurantreservationapi.adapters.usuario;

import br.com.postechfiap.restaurantreservationapi.adapters.AbstractAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAdapter extends AbstractAdapter<UsuarioRequest, Usuario> {

    public UsuarioAdapter(JsonUtils jsonUtils) {
        super(Usuario.class, jsonUtils);
    }
}
