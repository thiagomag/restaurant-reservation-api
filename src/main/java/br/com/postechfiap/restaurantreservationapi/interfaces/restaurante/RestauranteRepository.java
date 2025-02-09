package br.com.postechfiap.restaurantreservationapi.interfaces.restaurante;

import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Restaurante findByNome(String nome);

}
