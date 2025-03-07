package br.com.postechfiap.restaurantreservationapi.interfaces.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;

public interface ReservarMesaUseCase extends UseCase<ReservaRequest, ReservaResponse> {
}
