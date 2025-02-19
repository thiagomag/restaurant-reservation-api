package br.com.postechfiap.restaurantreservationapi.controller;


import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponseList;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliarReservaUseCase;
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
@RequestMapping(value = "/avaliar")
@RequiredArgsConstructor
@Validated
@Tag(name = "Avaliações", description = "API para gerenciar Avaliações")
public class AvaliacaoController {

    private final AvaliarReservaUseCase avaliarReservaUseCase;

    @PostMapping
    @Operation(summary = "Avaliar Reservas", description = "Avalia Reservas que já ocorreram.")
    public ResponseEntity<AvaliacaoResponse> avaliarReservas(@RequestBody @Valid AvaliacaoRequest request) {

        AvaliacaoResponse avaliacaoCriada = avaliarReservaUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoCriada);
    }



}
