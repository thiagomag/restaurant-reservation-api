package br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao;

import br.com.postechfiap.restaurantreservationapi.entities.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    boolean existsByReservaId(Long reservaId);

    List<Avaliacao> findByReserva_Usuario_IdAndDeletedTmspIsNull(Long id);

    List<Avaliacao> findByReserva_Restaurante_IdAndDeletedTmspIsNull(Long id);
}
