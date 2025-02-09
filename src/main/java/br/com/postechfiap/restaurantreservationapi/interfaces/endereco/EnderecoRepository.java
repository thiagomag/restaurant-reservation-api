package br.com.postechfiap.restaurantreservationapi.interfaces.endereco;

import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Endereco findByCep(String cep);
}
