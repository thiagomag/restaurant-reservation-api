package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaLocalizacaoRequest;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorLocalizacaoUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorNomeUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorTipoDeCozinhaUseCase;
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
    private final BuscarRestaurantesPorTipoDeCozinhaUseCase buscarRestaurantesPorTipoDeCozinhaUseCase;
    private final BuscarRestaurantesPorLocalizacaoUseCase buscarRestaurantesPorLocalizacaoUseCase;

    @PostMapping
    @Operation(summary = "Cadastrar Restaurante", description = "Cadastra um novo restaurante.")
    public RestauranteResponse cadastrarRestaurante(@RequestBody @Valid RestauranteRequest dto) {
        return cadastrarRestauranteUseCase.execute(dto);
    }

    @GetMapping("/findByName")
    @Operation(summary = "Buscar Restaurantes por Nome", description = "Busca restaurante por nome.")
    public ResponseEntity<List<RestauranteResponse>> buscarRestaurantePorNome(@RequestParam String name) {
        var response = buscarRestaurantesPorNomeUseCase.execute(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findByTipoCozinha")
    @Operation(summary = "Buscar Restaurantes por Tipo de Cozinha",
            description = "Busca restaurante por Tipo de Cozinha.")
    public ResponseEntity<List<RestauranteResponse>> buscarRestaurantePorTipoCozinha(@RequestParam  String tipoCozinha) {
        var response = buscarRestaurantesPorTipoDeCozinhaUseCase.execute(tipoCozinha);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/findByLocalizacao")
    @Operation(summary = "Buscar Restaurantes por Localizacao",
            description = "Busca restaurante por Localizacao.")
    public ResponseEntity<List<RestauranteResponse>> buscarRestaurantePorLocalizacao(@RequestBody @Valid RestauranteBuscaLocalizacaoRequest dto) {
        var response = buscarRestaurantesPorLocalizacaoUseCase.execute(dto);
        return ResponseEntity.ok(response);
    }




}
