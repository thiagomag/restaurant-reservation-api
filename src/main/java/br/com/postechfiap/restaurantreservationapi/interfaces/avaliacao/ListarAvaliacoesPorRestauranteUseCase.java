package br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;

import java.util.List;

public interface ListarAvaliacoesPorRestauranteUseCase extends UseCase<Long, List<AvaliacaoResponse>> {
}
