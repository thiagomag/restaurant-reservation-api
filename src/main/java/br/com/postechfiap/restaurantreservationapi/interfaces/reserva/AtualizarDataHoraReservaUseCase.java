package br.com.postechfiap.restaurantreservationapi.interfaces.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.gerenciamento.ReservaAtualizarDataHoraRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;

public interface AtualizarDataHoraReservaUseCase extends UseCase<ReservaAtualizarDataHoraRequest, ReservaResponse> {
}
