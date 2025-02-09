package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.CadastrarRestauranteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/restaurante")
@RequiredArgsConstructor
@Validated
@Tag(name = "Restaurante", description = "API para gerenciar restaurantes")
public class RestauranteController {

    private final CadastrarRestauranteUseCase cadastrarRestauranteUseCase;

    @PostMapping
    @Operation(summary = "Cadastrar Restaurante", description = "Cadastra um novo restaurante.")
    public RestauranteResponse cadastrarRestaurante(@RequestBody RestauranteRequest restauranteRequest) {
        final var response = cadastrarRestauranteUseCase.execute(restauranteRequest);
        return response;
    }
}
