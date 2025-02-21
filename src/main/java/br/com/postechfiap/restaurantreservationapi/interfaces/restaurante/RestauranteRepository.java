package br.com.postechfiap.restaurantreservationapi.interfaces.restaurante;

import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Restaurante findByNome(String nome);

    List<Restaurante> findByNomeContainingIgnoreCase(String nome);

}
