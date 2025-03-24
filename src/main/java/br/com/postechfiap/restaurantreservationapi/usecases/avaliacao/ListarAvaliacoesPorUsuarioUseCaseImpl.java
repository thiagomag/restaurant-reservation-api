package br.com.postechfiap.restaurantreservationapi.usecases.avaliacao;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.ListarAvaliacoesPorUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarAvaliacoesPorUsuarioUseCaseImpl implements ListarAvaliacoesPorUsuarioUseCase {

    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<AvaliacaoResponse> execute(Long usuarioId) {
        final var usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNotFoundException::new);
        final var avaliacoes = avaliacaoRepository.findByReserva_Usuario_IdAndDeletedTmspIsNull(usuario.getId());
        return avaliacoes.stream().map(AvaliacaoResponse::toDto).toList();
    }
}
