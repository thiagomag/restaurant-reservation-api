package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaLocalizacaoRequest;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final AtualizarCadastroRestauranteUseCase atualizarCadastroRestauranteUseCase;
    private final DeletarRestauranteUseCase deletarRestauranteUseCase;

    @PostMapping
    @Operation(summary = "Cadastrar Restaurante", description = "Cadastra um novo restaurante.")
    public ResponseEntity<RestauranteResponse> cadastrarRestaurante(@RequestBody @Valid RestauranteRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastrarRestauranteUseCase.execute(dto));
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

    @GetMapping("/findByLocalizacao")
    @Operation(summary = "Buscar Restaurantes por Localizacao",
            description = "Busca restaurante por Localizacao.")
    public ResponseEntity<List<RestauranteResponse>> buscarRestaurantePorLocalizacao(@RequestParam(required = false) String cep,
                                                                                     @RequestParam(required = false) String estado,
                                                                                     @RequestParam String cidade,
                                                                                     @RequestParam(required = false) String logradouro) {
        final var dto = RestauranteBuscaLocalizacaoRequest.builder()
                .cep(cep)
                .estado(estado)
                .cidade(cidade)
                .logradouro(logradouro)
                .build();
        var response = buscarRestaurantesPorLocalizacaoUseCase.execute(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Restaurante", description = "Atualiza um restaurante.")
    public RestauranteResponse atualizarRestaurante(@RequestBody RestauranteRequest dto,
                                                    @PathVariable Long id) {
        dto.setId(id);
        return atualizarCadastroRestauranteUseCase.execute(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Restaurante", description = "Deleta um restaurante.")
    public Void deletarRestaurante(@PathVariable Long id) {
        return deletarRestauranteUseCase.execute(id);
    }
}
