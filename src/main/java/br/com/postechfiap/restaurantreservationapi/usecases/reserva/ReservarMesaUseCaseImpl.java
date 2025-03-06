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
import br.com.postechfiap.restaurantreservationapi.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservarMesaUseCaseImpl implements ReservarMesaUseCase {

    private final UsuarioValidator usuarioValidator;
    private final RestauranteHelper restauranteHelper;
    private final MesaHelper mesaHelper;
    private final ReservaRepository reservaRepository;

    @Override
    public ReservaResponse execute(ReservaRequest reservaRequest) {
        // Passo 1: Validar se o Restaurante existe
        Restaurante restaurante = restauranteHelper.validateRestauranteExists(reservaRequest.getRestauranteId());

        // Passo 2: Validar se o Usuário existe
        Usuario usuario = usuarioValidator.validateUsuarioExists(reservaRequest.getUsuarioId());

        // Passo 3: Buscar as mesas disponíveis
        List<Mesa> mesasAReservar = mesaHelper.calculaMesasDisponiveisByRestaurante(
                reservaRequest.getRestauranteId(),
                reservaRequest.getNumeroDePessoas(),
                reservaRequest.getDataHoraReserva()
        );


        // Passo 4: Criar uma única reserva associando todas as mesas
        Reserva reserva = Reserva.builder()
                .mesas(mesasAReservar)
                .restaurante(restaurante)
                .usuario(usuario)
                .dataHoraReserva(reservaRequest.getDataHoraReserva()).numeroDePessoas(reservaRequest.getNumeroDePessoas()).build();

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