package br.com.postechfiap.restaurantreservationapi.utils;

import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservaHelper {

    private final ReservaRepository reservaRepository;

    public Reserva getReservaById(Long reservaId) {
        return reservaRepository.findById(reservaId)
                .orElseThrow(ReservaNotFoundException::new);
    }




}
