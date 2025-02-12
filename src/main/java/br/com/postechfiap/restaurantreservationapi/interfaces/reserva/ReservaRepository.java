package br.com.postechfiap.restaurantreservationapi.interfaces.reserva;

import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {
}
