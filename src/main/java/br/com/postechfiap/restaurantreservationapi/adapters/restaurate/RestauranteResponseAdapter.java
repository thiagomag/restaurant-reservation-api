package br.com.postechfiap.restaurantreservationapi.adapters.restaurate;

import br.com.postechfiap.restaurantreservationapi.adapters.AbstractAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestauranteResponseAdapter extends AbstractAdapter<Restaurante, RestauranteResponse> {

    private final EnderecoResponseAdapter enderecoResponseAdapter;

    public RestauranteResponseAdapter(JsonUtils jsonUtils, EnderecoResponseAdapter enderecoResponseAdapter) {
        super(RestauranteResponse.class, jsonUtils);
        this.enderecoResponseAdapter = enderecoResponseAdapter;
    }

    @Override
    protected ModelMapper getModelMapper() {
        if (this.modelMapper == null) {
            this.modelMapper = super.getModelMapper();

            this.modelMapper.typeMap(Restaurante.class, RestauranteResponse.class)
                    .addMappings(mapping -> {
                        mapping.using(toEnderecoResponse()).map(Restaurante::getEndereco, RestauranteResponse::setEndereco);
                    });
        }
        return this.modelMapper;
    }

    private Converter<Endereco, EnderecoResponse> toEnderecoResponse() {
        return context -> {
            final var endereco = context.getSource();
            return this.enderecoResponseAdapter.adapt(endereco);
        };
    }
}
