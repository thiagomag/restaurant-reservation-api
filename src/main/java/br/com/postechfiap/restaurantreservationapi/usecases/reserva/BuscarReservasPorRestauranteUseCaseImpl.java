package br.com.postechfiap.restaurantreservationapi.usecases.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.BuscarReservasPorRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarReservasPorRestauranteUseCaseImpl implements BuscarReservasPorRestauranteUseCase {

    private final RestauranteRepository restauranteRepository;
    private final ReservaRepository reservaRepository;


    @Override
    public List<ReservaResponse> execute(Long restauranteId) {
        final var restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(RestauranteNotFoundException::new);
        final var reservas = reservaRepository.findByRestauranteIdAndDeletedTmspIsNull(restaurante.getId());
        return reservas.stream()
                .map(ReservaResponse::toDto)
                .toList();
    }
}
