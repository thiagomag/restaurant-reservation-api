package br.com.postechfiap.restaurantreservationapi.usecases.usuario;

import br.com.postechfiap.restaurantreservationapi.adapters.usuario.UsuarioResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.BuscarUsuarioPorEmailUseCase;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
public class BuscarUsuarioPorEmailUseCaseImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioResponseAdapter usuarioResponseAdapter;

    private BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        buscarUsuarioPorEmailUseCase = new BuscarUsuarioPorEmailUseCaseImpl(usuarioRepository, usuarioResponseAdapter);
    }

    @Test
    public void buscaUsuarioPorEmailReturnSuccessfully() {
        //given
        final var email = "email";
        final var usuario = UsuarioTestUtils.buildUsuario();
        final var usuarioResponse = UsuarioTestUtils.buildUsuarioResponse();

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));
        when(usuarioResponseAdapter.adapt(usuario))
                .thenReturn(usuarioResponse);

        //when
        final var actual = buscarUsuarioPorEmailUseCase.execute(email);

        //then
        assertNotNull(actual);
        assertEquals(usuarioResponse, actual);
        assertEquals(usuarioResponse.getEmail(), actual.getEmail());
        assertEquals(usuarioResponse.getNome(), actual.getNome());
    }

    @Test
    public void buscaUsuarioPorEmailReturnError() {
        //given
        final var email = "email";

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> buscarUsuarioPorEmailUseCase.execute(email))
                .isInstanceOf(UsuarioNotFoundException.class);

        verifyNoInteractions(usuarioResponseAdapter);
    }
}
