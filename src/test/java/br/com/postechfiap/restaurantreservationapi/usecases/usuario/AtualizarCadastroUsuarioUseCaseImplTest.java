package br.com.postechfiap.restaurantreservationapi.usecases.usuario;

import br.com.postechfiap.restaurantreservationapi.adapters.usuario.UsuarioAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.usuario.UsuarioResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.AtualizarCadastroUsuarioUseCase;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
public class AtualizarCadastroUsuarioUseCaseImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioAdapter usuarioAdapter;
    @Mock
    private UsuarioResponseAdapter usuarioResponseAdapter;

    private AtualizarCadastroUsuarioUseCase atualizarCadastroUsuarioUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        atualizarCadastroUsuarioUseCase = new AtualizarCadastroUsuarioUseCaseImpl(usuarioRepository, usuarioAdapter, usuarioResponseAdapter);
    }

    @Test
    public void atualizaCadastroUsuarioReturnSuccessfully() {
        //given
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        final var usuario = UsuarioTestUtils.buildUsuario();
        final var usuarioResponse = UsuarioTestUtils.buildUsuarioResponse();

        when(usuarioRepository.findById(usuarioRequest.getId()))
                .thenReturn(Optional.of(usuario));
        when(usuarioAdapter.adapt(usuarioRequest, usuario))
                .thenReturn(usuario);
        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);
        when(usuarioResponseAdapter.adapt(usuario))
                .thenReturn(usuarioResponse);

        //when
        final var actual = atualizarCadastroUsuarioUseCase.execute(usuarioRequest);

        //then
        assertNotNull(actual);
        assertEquals(usuarioResponse, actual);
    }

    @Test
    public void atualizaCadastroUsuarioReturnError() {
        //given
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();

        when(usuarioRepository.findById(usuarioRequest.getId()))
                .thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> atualizarCadastroUsuarioUseCase.execute(usuarioRequest))
                .isInstanceOf(UsuarioNotFoundException.class);

        verifyNoInteractions(usuarioAdapter, usuarioResponseAdapter);
    }
}
