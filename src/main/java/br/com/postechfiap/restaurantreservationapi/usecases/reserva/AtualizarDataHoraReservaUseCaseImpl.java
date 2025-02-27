package br.com.postechfiap.restaurantreservationapi.usecases.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.gerenciamento.ReservaAtualizarDataHoraRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.AtualizarDataHoraReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtualizarDataHoraReservaUseCaseImpl implements AtualizarDataHoraReservaUseCase {

    private final ReservaRepository reservaRepository;
    private final ReservaHelper reservaHelper;
    private final MesaHelper mesaHelper;

    @Override
    public ReservaResponse execute(ReservaAtualizarDataHoraRequest dto) {
        // Passo 1: Buscar a reserva existente
        Reserva reserva = reservaHelper.getReservaById(dto.getReservaId());

        // Passo 2: Buscar novas mesas disponíveis para a nova data/hora
        List<Mesa> novasMesasDisponiveis = mesaHelper.calculaMesasDisponiveisByRestaurante(
                reserva.getRestaurante().getId(),
                reserva.getNumeroDePessoas(),
                dto.getDataAlteracao()
        );

        // Passo 3: Atualizar a reserva com as novas mesas e nova data/hora
        reserva.getMesas().clear();
        reserva.getMesas().addAll(novasMesasDisponiveis);
        reserva.setDataHoraReserva(dto.getDataAlteracao());

        // Passo 4: Salvar a reserva com as novas associações
        Reserva reservaAtualizada = reservaRepository.save(reserva);

        // Passo 5: Retornar a reserva atualizada como DTO
        return ReservaResponse.toDto(reservaAtualizada);
    }
}
