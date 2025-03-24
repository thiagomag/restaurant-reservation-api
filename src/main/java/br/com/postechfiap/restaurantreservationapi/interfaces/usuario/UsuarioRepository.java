package br.com.postechfiap.restaurantreservationapi.interfaces.usuario;

import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByIdAndDeletedTmspIsNull(Long id);

    Optional<Usuario> findByEmailAndDeletedTmspIsNull(String email);
}
