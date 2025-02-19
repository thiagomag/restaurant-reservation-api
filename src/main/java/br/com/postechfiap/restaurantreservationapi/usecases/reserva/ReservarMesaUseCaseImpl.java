package br.com.postechfiap.restaurantreservationapi.usecases.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponseList;
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
    public ReservaResponseList execute(ReservaRequest reservaRequest) {
        // Passo 1: Validar se o Restaurante existe
        Restaurante restaurante = restauranteHelper.validateRestauranteExists(reservaRequest.getRestauranteId());

        // Passo 2: Validar se o Usuário existe
        Usuario usuario = usuarioHelper.validateUsuarioExists(reservaRequest.getUsuarioId());

        // Passo 3: Verificar o número de mesas necessárias
        List<Mesa> mesasAReservar = mesaHelper.findMesasDisponiveis(
                reservaRequest.getRestauranteId(),
                reservaRequest.getNumeroDePessoas(),
                reservaRequest.getDataHoraReserva()
        );

        // Passo 4: Criar e salvar reservas no banco de dados
        List<Reserva> reservasCriadas = mesasAReservar.stream().map(mesa -> {
            Reserva reserva = new Reserva();
            reserva.setMesa(mesa);
            reserva.setRestaurante(restaurante);
            reserva.setUsuario(usuario);
            reserva.setDataHoraReserva(reservaRequest.getDataHoraReserva());
            reserva.setNumeroDePessoas(reservaRequest.getNumeroDePessoas());

            return reservaRepository.save(reserva);
        }).toList();

        // Passo 5: Retornar a resposta formatada usando ReservaResponse.toList
        return ReservaResponseList.builder()
                .usuarioId(usuario.getId())
                .restauranteId(restaurante.getId())
                .reservas(ReservaResponse.toList(reservasCriadas))
                .build();
    }
}