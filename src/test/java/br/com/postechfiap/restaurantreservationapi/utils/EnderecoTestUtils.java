package br.com.postechfiap.restaurantreservationapi.utils;

import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Endereco;

public class EnderecoTestUtils {

    public static EnderecoRequest buildEnderecoRequest() {
        return EnderecoRequest.builder()
                .logradouro("Rua dos Bobos")
                .numero(0)
                .bairro("Vila do Chaves")
                .cidade("Sao Paulo")
                .estado("SP")
                .cep("00000-000")
                .build();
    }

    public static Endereco buildEndereco() {
        return Endereco.builder()
                .id(1L)
                .logradouro("Rua dos Bobos")
                .numero(0)
                .bairro("Vila do Chaves")
                .cidade("Sao Paulo")
                .estado("SP")
                .cep("00000-000")
                .build();
    }

    public static EnderecoResponse buildEnderecoResponse() {
        return EnderecoResponse.builder()
                .id(1L)
                .logradouro("Rua dos Bobos")
                .numero(0)
                .bairro("Vila do Chaves")
                .cidade("Sao Paulo")
                .estado("SP")
                .cep("00000-000")
                .build();
    }
}
