package br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;

public interface AvaliarReservaUseCase extends UseCase<AvaliacaoRequest, AvaliacaoResponse> {
}
