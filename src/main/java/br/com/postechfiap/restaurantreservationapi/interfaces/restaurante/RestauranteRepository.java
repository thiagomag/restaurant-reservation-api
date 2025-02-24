package br.com.postechfiap.restaurantreservationapi.interfaces.restaurante;

import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Restaurante findByNome(String nome);

    // 1. UseCase BuscarRestaurantesPorNome
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);

    // 2. UseCase BuscarRestaurantesPorTipoDeCozinhaUseCase
    List<Restaurante> findByTipoCozinha(TiposCozinhaEnum tipoCozinha);

    // 3. UseCase BuscarRestaurantesPorLocalizacao
    @Query("SELECT r FROM Restaurante r JOIN r.endereco e " +
            "WHERE (:logradouro IS NULL OR e.logradouro LIKE %:logradouro%) " +
            "AND (:cep IS NULL OR e.cep LIKE %:cep%) " +
            "AND (:cidade IS NULL OR e.cidade LIKE %:cidade%) " +
            "AND (:estado IS NULL OR e.estado LIKE %:estado%)")
    List<Restaurante> findByLocalizacao(@Param("logradouro") String logradouro,
                                        @Param("cep") String cep,
                                        @Param("cidade") String cidade,
                                        @Param("estado") String estado);


}
