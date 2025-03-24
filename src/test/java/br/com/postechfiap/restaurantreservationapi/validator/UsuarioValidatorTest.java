package br.com.postechfiap.restaurantreservationapi.validator;

import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioValidatorTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private UsuarioValidator usuarioValidator;

    @BeforeEach
    void setUp() {
        // Inicializa o validador com o repositório mockado
        usuarioValidator = new UsuarioValidator(usuarioRepository);
    }

    @Test
    void deveLancarExcecao_SeUsuarioNaoExistir() {
        // Arrange
        Long usuarioId = 1L;


        when(usuarioRepository.findByIdAndDeletedTmspIsNull(usuarioId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> usuarioValidator.validateUsuarioExists(usuarioId))
                .isInstanceOf(UsuarioNotFoundException.class)
                .hasMessageContaining("Usuário não encontrado");
    }

    @Test
    void deveRetornarUsuario_SeUsuarioExistir() {
        // Arrange
        Long usuarioId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        when(usuarioRepository.findByIdAndDeletedTmspIsNull(usuarioId)).thenReturn(Optional.of(usuario));

        // Act
        Usuario resultado = usuarioValidator.validateUsuarioExists(usuarioId);

        // Assert
        assertThat(resultado).isEqualTo(usuario); // Verifica se o usuário retornado é o mesmo que foi mockado
    }
}
