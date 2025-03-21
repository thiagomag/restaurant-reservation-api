package br.com.postechfiap.restaurantreservationapi.utils.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.utils.endereco.EnderecoTestUtils;

public class RestauranteTestUtils {

    public static RestauranteRequest buildRestauranteRequest() {
        return RestauranteRequest.builder()
                .nome("Restaurante Teste")
                .tipoCozinha(TiposCozinhaEnum.BAIANA)
                .horarioFuncionamento("10:00 - 22:00")
                .capacidade(100)
                .endereco(EnderecoTestUtils.buildEnderecoRequest())
                .build();
    }

    public static Restaurante buildRestaurante() {
        return Restaurante.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoCozinha(TiposCozinhaEnum.BAIANA)
                .horarioFuncionamento("10:00 - 22:00")
                .capacidade(100)
                .endereco(EnderecoTestUtils.buildEndereco())
                .build();
    }

    public static RestauranteResponse buildRestauranteResponse() {
        final var restauranteResponse = new RestauranteResponse();
        restauranteResponse.setId(1L);
        restauranteResponse.setNome("Restaurante Teste");
        restauranteResponse.setTipoCozinha(TiposCozinhaEnum.BAIANA);
        restauranteResponse.setHorarioFuncionamento("10:00 - 22:00");
        restauranteResponse.setCapacidade(100);
        restauranteResponse.setEndereco(EnderecoTestUtils.buildEnderecoResponse());
        return restauranteResponse;
    }

}
