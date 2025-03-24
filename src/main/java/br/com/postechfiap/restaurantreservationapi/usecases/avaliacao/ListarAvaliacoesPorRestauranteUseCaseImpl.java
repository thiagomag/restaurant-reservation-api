package br.com.postechfiap.restaurantreservationapi.usecases.avaliacao;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.ListarAvaliacoesPorRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarAvaliacoesPorRestauranteUseCaseImpl implements ListarAvaliacoesPorRestauranteUseCase {

    private final AvaliacaoRepository avaliacaoRepository;
    private final RestauranteRepository restauranteRepository;

    @Override
    public List<AvaliacaoResponse> execute(Long restauranteId) {
        final var restaurante = restauranteRepository.findById(restauranteId).orElseThrow(RestauranteNotFoundException::new);
        final var avaliacoes = avaliacaoRepository.findByReserva_Restaurante_IdAndDeletedTmspIsNull(restaurante.getId());
        return avaliacoes.stream().map(AvaliacaoResponse::toDto).toList();
    }
}
