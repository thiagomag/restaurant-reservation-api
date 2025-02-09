package br.com.postechfiap.restaurantreservationapi.adapters.endereco;

import br.com.postechfiap.restaurantreservationapi.adapters.AbstractAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import org.springframework.stereotype.Component;

@Component
public class EnderecoResponseAdapter extends AbstractAdapter<Endereco, EnderecoResponse>  {

    public EnderecoResponseAdapter(JsonUtils jsonUtils) {
        super(EnderecoResponse.class, jsonUtils);
    }
}
