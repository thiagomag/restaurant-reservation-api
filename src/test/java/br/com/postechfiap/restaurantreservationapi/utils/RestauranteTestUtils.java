package br.com.postechfiap.restaurantreservationapi.utils;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;

public class RestauranteTestUtils {

    public static RestauranteRequest buildRestauranteRequest() {
        return RestauranteRequest.builder()
                .nome("Restaurante Teste")
                .tipoCozinha(TiposCozinhaEnum.BAIANA)
                .horarioFuncionamento("10:00 - 22:00")
                .capacidade("100")
                .endereco(EnderecoTestUtils.buildEnderecoRequest())
                .build();
    }

    public static Restaurante buildRestaurante() {
        return Restaurante.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoCozinha(TiposCozinhaEnum.BAIANA)
                .horarioFuncionamento("10:00 - 22:00")
                .capacidade("100")
                .endereco(EnderecoTestUtils.buildEndereco())
                .build();
    }

    public static RestauranteResponse buildRestauranteResponse() {
        return RestauranteResponse.builder()
                .id(1L)
                .nome("Restaurante Teste")
                .tipoCozinha(TiposCozinhaEnum.BAIANA)
                .horarioFuncionamento("10:00 - 22:00")
                .capacidade("100")
                .endereco(EnderecoTestUtils.buildEnderecoResponse())
                .build();
    }

}
