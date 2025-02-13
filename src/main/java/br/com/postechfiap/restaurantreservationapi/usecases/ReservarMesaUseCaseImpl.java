package br.com.postechfiap.restaurantreservationapi.usecases;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.exception.mesa.MesaIndisponivelException;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.MesaRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservarMesaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteHelper;
import br.com.postechfiap.restaurantreservationapi.utils.UsuarioHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservarMesaUseCaseImpl implements ReservarMesaUseCase {

    private final RestauranteHelper restauranteHelper;
    private final UsuarioHelper usuarioHelper;
    private final MesaHelper mesaHelper;
    private final ReservaRepository reservaRepository;


    @Override
    public ReservaResponse execute(ReservaRequest reservaRequest) {
        /// Passo 1: Validar se o Restaurante existe
        Restaurante restaurante = restauranteHelper.validateRestauranteExists(reservaRequest.getRestauranteId());

        // Passo 2: Validar se o Usuário existe
        Usuario usuario = usuarioHelper.validateUsuarioExists(reservaRequest.getUsuarioId());

        // Passo 3: Verificar o número de mesas necessárias
        List<Mesa> mesasDisponiveis = mesaHelper.findMesasDisponiveis(
                reservaRequest.getRestauranteId(), reservaRequest.getNumeroDePessoas());

        if (mesasDisponiveis.size() < reservaRequest.getNumeroDePessoas() / 2) {
            throw new MesaIndisponivelException("Não há mesas suficientes para o número de pessoas solicitadas.");
        }


        // Passo 4: Criar a Reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setRestaurante(restaurante);
        reserva.setMesas(mesa);
        reserva.setDataHoraReserva(reservaRequest.getDataHoraReserva());

        // Passo 5: Salvar a Reserva no banco
        reservaRepository.save(reserva);


        mesaRepository.save(mesa);

        // Passo 7: Retornar a resposta com os dados da reserva
        return new ReservaResponse(
                reserva.getId(),
                reserva.getUsuario().getId(),
                reserva.getMesa().getId(),
                reserva.getDataHoraReserva()
        );
    }
}