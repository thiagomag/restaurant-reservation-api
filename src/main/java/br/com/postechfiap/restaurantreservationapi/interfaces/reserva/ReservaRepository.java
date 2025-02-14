package br.com.postechfiap.restaurantreservationapi.interfaces.reserva;

import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {

    List<Reserva> findByRestauranteIdAndDataHoraReservaBetween(
            Long restauranteId, LocalDateTime inicioIntervalo, LocalDateTime fimIntervalo);


}
