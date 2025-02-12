package br.com.postechfiap.restaurantreservationapi.interfaces.mesa;

import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaRepository extends JpaRepository<Mesa,Long> {
}
