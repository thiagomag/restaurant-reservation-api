package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteRequest;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.CadastrarMesaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mesas")
@RequiredArgsConstructor
@Validated
@Tag(name = "Mesa", description = "API para gerenciar Mesas")
public class MesaController {

    private final CadastrarMesaUseCase cadastrarMesaUseCase;

    @PostMapping
    @Operation(summary = "Cadastrar Mesas", description = "Cadastra novas mesas para um restaurante.")
    public ResponseEntity<MesaResponse> cadastrarMesas(@RequestBody @Valid MesaRequest request) {
        MesaResponse mesasCriadas = cadastrarMesaUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesasCriadas);
    }
}