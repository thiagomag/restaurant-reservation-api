package br.com.postechfiap.restaurantreservationapi.usecases.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservarMesaUseCase;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteHelper;
import br.com.postechfiap.restaurantreservationapi.utils.UsuarioHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservarMesaUseCaseImpl implements ReservarMesaUseCase {

    private final RestauranteHelper restauranteHelper;
    private final UsuarioHelper usuarioHelper;
    private final MesaHelper mesaHelper;
    private final ReservaRepository reservaRepository;

    @Override
    public ReservaResponse execute(ReservaRequest reservaRequest) {
        // Passo 1: Validar se o Restaurante existe
        Restaurante restaurante = restauranteHelper.validateRestauranteExists(reservaRequest.getRestauranteId());

        // Passo 2: Validar se o Usuário existe
        Usuario usuario = usuarioHelper.validateUsuarioExists(reservaRequest.getUsuarioId());

        // Passo 3: Buscar as mesas disponíveis
        List<Mesa> mesasAReservar = mesaHelper.calculaMesasDisponiveisByRestaurante(
                reservaRequest.getRestauranteId(),
                reservaRequest.getNumeroDePessoas(),
                reservaRequest.getDataHoraReserva()
        );

        if (mesasAReservar.isEmpty()) {
            throw new RuntimeException("Não há mesas disponíveis para a reserva.");
        }

        // Passo 4: Criar uma única reserva associando todas as mesas
        Reserva reserva = new Reserva();
        reserva.setMesas(mesasAReservar);
        reserva.setRestaurante(restaurante);
        reserva.setUsuario(usuario);
        reserva.setDataHoraReserva(reservaRequest.getDataHoraReserva());
        reserva.setNumeroDePessoas(reservaRequest.getNumeroDePessoas());

        reserva = reservaRepository.save(reserva);
        // Passo 5: Retornar a resposta formatada
        return ReservaResponse.builder()
                .reservaId(reserva.getId())
                .usuarioId(usuario.getId())
                .restauranteName(restaurante.getNome())
                .numeroDePessoas(reserva.getNumeroDePessoas())
                .dataHoraReserva(reserva.getDataHoraReserva())
                .mesas(reserva.getMesas().stream().map(Mesa::getId).collect(Collectors.toList())) // Retorna os IDs das mesas reservadas
                .build();
    }
}