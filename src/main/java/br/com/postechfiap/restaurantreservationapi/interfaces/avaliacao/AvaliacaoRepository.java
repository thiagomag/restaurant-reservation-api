package br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao;

import br.com.postechfiap.restaurantreservationapi.entities.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    boolean existsByReservaId(Long reservaId);
}
