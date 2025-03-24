package br.com.postechfiap.restaurantreservationapi.usecases.avaliacao;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Avaliacao;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.enuns.NotaEnum;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliarReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import br.com.postechfiap.restaurantreservationapi.validator.AvaliacaoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliarReservaUseCaseImpl implements AvaliarReservaUseCase {


    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoValidator avaliacaoValidator;

    private final ReservaHelper reservaHelper;


    @Override
    public AvaliacaoResponse execute(AvaliacaoRequest request) {

        // 1. Validar os dados de entrada com o serviço de validação
        avaliacaoValidator.validarNota(request.getNota());

        // 2. Verificar se a reserva já foi avaliada
        avaliacaoValidator.validarSeReservaJaFoiAvaliada(request.getReservaId());

        // 3. Usar o ReservaHelper para buscar a reserva
        Reserva reserva = reservaHelper.getReservaById(request.getReservaId());

        // 4. Criar uma nova avaliação com os dados da entrada
        Avaliacao avaliacao = new Avaliacao(
                reserva,
                NotaEnum.fromValor(request.getNota()),
                request.getComentario()
        );

        // 5. Salvar a avaliação no banco de dados
        Avaliacao savedAvaliacao = avaliacaoRepository.save(avaliacao);

        // 6. Retornar a resposta com os dados da avaliação criada
        return new AvaliacaoResponse(
                savedAvaliacao.getId(),
                savedAvaliacao.getReserva().getId(),
                savedAvaliacao.getReserva().getUsuario().getId(),
                savedAvaliacao.getReserva().getRestaurante().getNome(),
                savedAvaliacao.getNota().getValor(),
                savedAvaliacao.getComentario());
    }




}