package br.com.postechfiap.restaurantreservationapi.adapters.usuario;

import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import br.com.postechfiap.restaurantreservationapi.utils.usuario.UsuarioTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioResponseAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var usuario = UsuarioTestUtils.buildUsuario();

        //when
        final var actual = new UsuarioResponseAdapter(jsonUtils).adapt(usuario);

        //then
        assertEquals(usuario.getEmail(), actual.getEmail());
        assertEquals(usuario.getNome(), actual.getNome());
        assertEquals(usuario.getTelefone(), actual.getTelefone());
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach2() {
        //given
        final var usuario = UsuarioTestUtils.buildUsuario();
        final var usuarioResponse = UsuarioTestUtils.buildUsuarioResponse();

        //when
        final var actual = new UsuarioResponseAdapter(jsonUtils).adapt(usuario, usuarioResponse);

        //then
        assertEquals(usuario.getEmail(), actual.getEmail());
        assertEquals(usuario.getNome(), actual.getNome());
        assertEquals(usuario.getTelefone(), actual.getTelefone());
    }
}
