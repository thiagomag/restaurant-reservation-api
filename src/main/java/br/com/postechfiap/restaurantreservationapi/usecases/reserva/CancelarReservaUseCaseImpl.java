package br.com.postechfiap.restaurantreservationapi.usecases.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.gerenciamento.ReservaCancelamentoRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.CancelarReservaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CancelarReservaUseCaseImpl implements CancelarReservaUseCase {

    private final ReservaHelper reservaHelper;
    private final ReservaRepository reservaRepository;

    @Override
    public String execute(Long idDelete) {
        // Usando o helper para buscar a reserva
        Reserva reserva = reservaHelper.getReservaById(idDelete);

        // Realizando o delete da reserva
        reservaRepository.deleteById(idDelete);

        // Retornando a mensagem de sucesso
        return "Reserva com ID " + idDelete + " foi deletada com sucesso.";
    }
}