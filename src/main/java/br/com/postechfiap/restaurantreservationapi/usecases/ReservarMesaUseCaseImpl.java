package br.com.postechfiap.restaurantreservationapi.usecases;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservarMesaUseCase;

public class ReservarMesaUseCaseImpl implements ReservarMesaUseCase {

    @Override
    public ReservaResponse execute(ReservaRequest entry) {
        return null;
    }

    @Override
    public ReservaResponse execute(ReservaRequest entry, Object... args) {
        //return ReservarMesaUseCase.super.execute(entry, args);
        return null;
    }
}
