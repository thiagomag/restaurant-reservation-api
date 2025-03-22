package br.com.postechfiap.restaurantreservationapi.usecases.usuario;

import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.DeletarUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import br.com.postechfiap.restaurantreservationapi.utils.usuario.UsuarioTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class DeletarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private DeletarUsuarioUseCase deletarUsuarioUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deletarUsuarioUseCase = new DeletarUsuarioUseCaseImpl(usuarioRepository);
    }

    @Test
    public void deletaUsuarioReturnSuccessfully() {
        //given
        final var usuario = UsuarioTestUtils.buildUsuario();

        when(usuarioRepository.findById(usuario.getId()))
                .thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario))
                .thenAnswer(invocation -> {
                    final var usuarioInDB = (Usuario) invocation.getArgument(0);

                    assertNotNull(usuarioInDB.getDeletedTmsp());
                    return usuarioInDB;
                });

        //when
        deletarUsuarioUseCase.execute(usuario.getId());

        //then
        verify(usuarioRepository).findById(usuario.getId());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    public void deletaUsuarioReturnError() {
        //given
        final var usuario = UsuarioTestUtils.buildUsuario();

        when(usuarioRepository.findById(usuario.getId()))
                .thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> deletarUsuarioUseCase.execute(usuario.getId()))
                .isInstanceOf(UsuarioNotFoundException.class);
        verify(usuarioRepository, times(1)).findById(usuario.getId());
        verify(usuarioRepository ,times(0)).save(usuario);
    }
}
