package br.com.postechfiap.restaurantreservationapi.interfaces.mesa;

import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponseList;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;

public interface CadastrarMesaUseCase extends UseCase<MesaRequest, MesaResponseList> {
}
