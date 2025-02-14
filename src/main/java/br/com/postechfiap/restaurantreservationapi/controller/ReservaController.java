package br.com.postechfiap.restaurantreservationapi.controller;


import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.CadastrarMesaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservarMesaUseCase;
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
@RequestMapping(value = "/reserva")
@RequiredArgsConstructor
@Validated
@Tag(name = "Reserva", description = "API para gerenciar as reservas dos restaurantes")
public class ReservaController {

    private final ReservarMesaUseCase reservarMesaUseCase;

    @PostMapping
    @Operation(summary = "Reserva de Mesas em Restaurante", description = "Cadastra novas mesas para um restaurante.")
    public ResponseEntity<ReservaResponse> cadastrarMesas(@RequestBody @Valid ReservaRequest request) {
        ReservaResponse reservaCriada = reservarMesaUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaCriada);
    }
}
