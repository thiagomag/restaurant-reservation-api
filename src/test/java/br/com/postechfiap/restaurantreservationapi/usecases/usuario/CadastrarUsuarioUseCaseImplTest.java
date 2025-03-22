package br.com.postechfiap.restaurantreservationapi.usecases.usuario;

import br.com.postechfiap.restaurantreservationapi.adapters.usuario.UsuarioAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.usuario.UsuarioResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.CadastrarUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.UsuarioRepository;
import br.com.postechfiap.restaurantreservationapi.utils.usuario.UsuarioTestUtils;
import br.com.postechfiap.restaurantreservationapi.validator.UsuarioValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
public class CadastrarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioAdapter usuarioAdapter;
    @Mock
    private UsuarioResponseAdapter usuarioResponseAdapter;
    @Mock
    private UsuarioValidator usuarioValidator;

    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cadastrarUsuarioUseCase = new CadastrarUsuarioUseCaseImpl(usuarioRepository, usuarioAdapter
                ,usuarioResponseAdapter, usuarioValidator);
    }

    @Test
    public void cadastraUsuarioReturnSuccessfully() {
        //given
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        final var usuario = UsuarioTestUtils.buildUsuario();
        final var usuarioResponse = UsuarioTestUtils.buildUsuarioResponse();

        when(usuarioAdapter.adapt(usuarioRequest))
                .thenReturn(usuario);
        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);
        when(usuarioResponseAdapter.adapt(usuario))
                .thenReturn(usuarioResponse);

        //when
        final var actual = cadastrarUsuarioUseCase.execute(usuarioRequest);

        //then
        assertNotNull(actual);
        assertEquals(usuarioResponse, actual);
        assertEquals(usuarioResponse.getId(), actual.getId());
        assertEquals(usuarioResponse.getNome(), actual.getNome());
        assertEquals(usuarioResponse.getEmail(), actual.getEmail());
    }
}
