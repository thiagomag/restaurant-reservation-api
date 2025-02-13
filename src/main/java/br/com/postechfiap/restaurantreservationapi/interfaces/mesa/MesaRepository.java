package br.com.postechfiap.restaurantreservationapi.interfaces.mesa;

import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa,Long> {
    List<Mesa> findByRestauranteId(Long restauranteId);

    Optional<Mesa> findTopByRestauranteIdOrderByNumeroMesaDesc(Long restauranteId);



}


