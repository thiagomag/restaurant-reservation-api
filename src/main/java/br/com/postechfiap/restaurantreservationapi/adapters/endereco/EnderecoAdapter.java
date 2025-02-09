package br.com.postechfiap.restaurantreservationapi.adapters.endereco;

import br.com.postechfiap.restaurantreservationapi.adapters.AbstractAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class EnderecoAdapter extends AbstractAdapter<EnderecoRequest, Endereco> {

    public EnderecoAdapter(JsonUtils jsonUtils) {
        super(Endereco.class, jsonUtils);
    }
}
