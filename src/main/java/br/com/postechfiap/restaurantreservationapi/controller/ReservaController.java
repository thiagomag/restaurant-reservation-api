package br.com.postechfiap.restaurantreservationapi.controller;


import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.gerenciamento.ReservaAtualizarDataHoraRequest;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.AtualizarDataHoraReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.BuscarReservasPorRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.CancelarReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservarMesaUseCase;
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
@RequestMapping(value = "/reserva")
@RequiredArgsConstructor
@Validated
@Tag(name = "Gerenciamento de Reserva", description = "API para gerenciar as reservas dos restaurantes")
public class ReservaController {

    private final ReservarMesaUseCase reservarMesaUseCase;
    private final BuscarReservasPorRestauranteUseCase buscarReservasPorRestauranteUseCase;
    private final CancelarReservaUseCase cancelarReservaUseCase;
    private final AtualizarDataHoraReservaUseCase atualizarDataHoraReservaUseCase;

    @PostMapping
    @Operation(summary = "Reserva de Mesas em Restaurante", description = "Cadastra novas mesas para um restaurante.")
    public ResponseEntity<ReservaResponse> executarReservas(@RequestBody @Valid ReservaRequest request) {
        ReservaResponse reservaCriada = reservarMesaUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaCriada);
    }

    @GetMapping("/findByRestauranteId")
    @Operation(summary = "Buscar Reserva de Mesas em Restaurante", description = "Busca reservas para um restaurante.")
    public ResponseEntity<List<ReservaResponse>> buscarReserva(@RequestParam Long restauranteId) {
        final var reserva = buscarReservasPorRestauranteUseCase.execute(restauranteId);
        return ResponseEntity.ok(reserva);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Reserva de Mesas em Restaurante", description = "Deletar reservas para um restaurante.")
    public ResponseEntity<String> deletarReserva(@PathVariable Long id) {
        String resposta = cancelarReservaUseCase.execute(id);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping
    @Operation(summary = "Atualizar Reserva em data e hora de Mesas em Restaurante",
            description = "Atualizar reserva em data e hora para um restaurante.")
    public ResponseEntity<ReservaResponse> atualizarReservaByDataHora
            (@RequestBody @Valid ReservaAtualizarDataHoraRequest dto) {

        var resposta = atualizarDataHoraReservaUseCase.execute(dto);
        return ResponseEntity.ok(resposta);
    }





}
