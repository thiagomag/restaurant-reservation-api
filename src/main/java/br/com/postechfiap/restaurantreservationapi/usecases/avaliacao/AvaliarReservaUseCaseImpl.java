package br.com.postechfiap.restaurantreservationapi.usecases.avaliacao;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Avaliacao;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.enuns.NotaEnum;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliarReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliarReservaUseCaseImpl implements AvaliarReservaUseCase {

    private final ReservaHelper reservaHelper;
    private final AvaliacaoRepository avaliacaoRepository;

    @Override
    public AvaliacaoResponse execute(AvaliacaoRequest request) {
        // 1. Validar os dados de entrada
        if (request.getReservaId() == null || request.getNota() == null || request.getNota() < 1 || request.getNota() > 5) {
            throw new IllegalArgumentException("Dados inválidos para a avaliação.");
        }

        // 2. Usar o ReservaHelper para buscar a reserva
        Reserva reserva = reservaHelper.getReservaById(request.getReservaId());

        // 3. Criar uma nova avaliação com os dados da entrada
        Avaliacao avaliacao = new Avaliacao(
                reserva,
                NotaEnum.fromValor(request.getNota()),
                request.getComentario()
        );

        // 4. Salvar a avaliação no banco de dados
        Avaliacao savedAvaliacao = avaliacaoRepository.save(avaliacao);

        // 5. Retornar a resposta com os dados da avaliação criada
        return new AvaliacaoResponse(
                savedAvaliacao.getId(),
                savedAvaliacao.getReserva().getId(),
                savedAvaliacao.getRestauranteId(),
                savedAvaliacao.getNota().getValor(),
                savedAvaliacao.getComentario()

        );
    }
}