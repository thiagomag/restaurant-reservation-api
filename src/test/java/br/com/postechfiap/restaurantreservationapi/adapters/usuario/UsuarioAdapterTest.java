package br.com.postechfiap.restaurantreservationapi.adapters.usuario;

import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import br.com.postechfiap.restaurantreservationapi.utils.usuario.UsuarioTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();

        //when
        final var actual = new UsuarioAdapter(jsonUtils).adapt(usuarioRequest);

        //then
        assertEquals(usuarioRequest.getEmail(), actual.getEmail());
        assertEquals(usuarioRequest.getNome(), actual.getNome());
        assertEquals(usuarioRequest.getTelefone(), actual.getTelefone());
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach2() {
        //given
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        usuarioRequest.setEmail("novoemail@email.com");
        final var usuario = UsuarioTestUtils.buildUsuario();

        //when
        final var actual = new UsuarioAdapter(jsonUtils).adapt(usuarioRequest, usuario);

        //then
        assertEquals(usuarioRequest.getEmail(), actual.getEmail());
        assertEquals(usuarioRequest.getNome(), actual.getNome());
        assertEquals(usuarioRequest.getTelefone(), actual.getTelefone());
    }
}
