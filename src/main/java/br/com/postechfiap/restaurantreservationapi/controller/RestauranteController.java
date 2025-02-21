package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaNomeRequest;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorNomeUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.CadastrarRestauranteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurante")
@RequiredArgsConstructor
@Validated
@Tag(name = "Restaurante", description = "API para gerenciar restaurantes")
public class RestauranteController {

    private final CadastrarRestauranteUseCase cadastrarRestauranteUseCase;
    private final BuscarRestaurantesPorNomeUseCase buscarRestaurantesPorNomeUseCase;

    @PostMapping
    @Operation(summary = "Cadastrar Restaurante", description = "Cadastra um novo restaurante.")
    public RestauranteResponse cadastrarRestaurante(@RequestBody @Valid RestauranteRequest restauranteRequest) {
        final var response = cadastrarRestauranteUseCase.execute(restauranteRequest);
        return response;
    }

    @PostMapping("/findByName")
    @Operation(summary = "Buscar Restaurantes por Nome", description = "Busca restaurante por nome.")
    public ResponseEntity<List<RestauranteResponse>> buscarRestaurantePorNome
            (@RequestBody @Valid RestauranteBuscaNomeRequest restauranteBuscaNomeRequest) {

        var response = buscarRestaurantesPorNomeUseCase.execute(restauranteBuscaNomeRequest);

        return ResponseEntity.ok(response);
    }



}
