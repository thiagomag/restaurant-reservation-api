package br.com.postechfiap.restaurantreservationapi.controller;


import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliarReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.ListarAvaliacoesPorRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.ListarAvaliacoesPorUsuarioUseCase;
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
@RequestMapping(value = "/avaliar")
@RequiredArgsConstructor
@Validated
@Tag(name = "Avaliações", description = "API para gerenciar Avaliações")
public class AvaliacaoController {

    private final AvaliarReservaUseCase avaliarReservaUseCase;
    private final ListarAvaliacoesPorUsuarioUseCase listarAvaliacoesPorUsuarioUseCase;
    private final ListarAvaliacoesPorRestauranteUseCase listarAvaliacoesPorRestauranteUseCase;

    @PostMapping
    @Operation(summary = "Avaliar Reservas", description = "Avalia Reservas que já ocorreram.")
    public ResponseEntity<AvaliacaoResponse> avaliarReservas(@RequestBody @Valid AvaliacaoRequest request) {

        AvaliacaoResponse avaliacaoCriada = avaliarReservaUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoCriada);
    }

    @GetMapping("/findByUsuario")
    @Operation(summary = "Listar Avaliações por Usuario", description = "Lista todas as avaliações por usuario.")
    public ResponseEntity<List<AvaliacaoResponse>> listarAvaliacoesPorUsuario(@RequestParam Long usuarioId) {
        final var avaliacoes = listarAvaliacoesPorUsuarioUseCase.execute(usuarioId);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/findByRestaurante")
    @Operation(summary = "Listar Avaliações por Restaurante", description = "Lista todas as avaliações por restaurante.")
    public ResponseEntity<List<AvaliacaoResponse>> listarAvaliacoesPorRestaurante(@RequestParam Long restauranteId) {
        final var avaliacoes = listarAvaliacoesPorRestauranteUseCase.execute(restauranteId);
        return ResponseEntity.ok(avaliacoes);
    }

}
