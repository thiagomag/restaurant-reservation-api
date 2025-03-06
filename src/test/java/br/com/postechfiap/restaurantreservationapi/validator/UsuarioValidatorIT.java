package br.com.postechfiap.restaurantreservationapi.validator;

import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test") // Usa o perfil de testes para não afetar o banco real
@Transactional // Garante rollback após cada teste
public class UsuarioValidatorIT {

    @Autowired
    private UsuarioValidator usuarioValidator;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Deve retornar um usuário quando ele existe no banco")
    void deveRetornarUsuarioExistente() {
        Usuario usuarioEncontrado = usuarioValidator.validateUsuarioExists(1L);

        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado.getId()).isEqualTo(1L);
        assertThat(usuarioEncontrado.getNome()).isEqualTo("Carlos Silva");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não existe")
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        Long idInexistente = 999L;

        assertThatThrownBy(() -> usuarioValidator.validateUsuarioExists(idInexistente))
                .isInstanceOf(UsuarioNotFoundException.class);
    }
}