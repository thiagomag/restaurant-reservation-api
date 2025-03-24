package br.com.postechfiap.restaurantreservationapi.interfaces.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;

import java.util.List;

public interface BuscarReservasPorRestauranteUseCase extends UseCase<Long, List<ReservaResponse>> {
}
