package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioRequest;
import br.com.postechfiap.restaurantreservationapi.exception.GlobalExceptionHandler;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.AtualizarCadastroUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.BuscarUsuarioPorEmailUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.CadastrarUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.DeletarUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.utils.usuario.UsuarioTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class UsuarioControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    @Mock
    private BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase;
    @Mock
    private AtualizarCadastroUsuarioUseCase atualizarCadastroUsuarioUseCase;
    @Mock
    private DeletarUsuarioUseCase deletarUsuarioUseCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    void deveCadastrarUsuarioComSucesso() throws Exception {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        final var usuarioResponse = UsuarioTestUtils.buildUsuarioResponse();

        when(cadastrarUsuarioUseCase.execute(any(UsuarioRequest.class)))
                .thenReturn(usuarioResponse);

        final var requestJson = new ObjectMapper().writeValueAsString(usuarioRequest);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(result -> {
                    final var content = result.getResponse().getContentAsString();
                    final var nome = usuarioResponse.getNome();
                    final var email = usuarioResponse.getEmail();
                    final var telefone = usuarioResponse.getTelefone();
                    final var id = usuarioResponse.getId();

                    assert content.contains(nome);
                    assert content.contains(email);
                    assert content.contains(telefone);
                    assert content.contains(String.valueOf(id));
                });

        verify(cadastrarUsuarioUseCase, times(1)).execute(any(UsuarioRequest.class));
    }

    @Test
    void deveRetornarErroQuandoNomeForNulo() throws Exception {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        usuarioRequest.setNome(null);

        final var requestJson = new ObjectMapper().writeValueAsString(usuarioRequest);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verify(cadastrarUsuarioUseCase, never()).execute(any(UsuarioRequest.class));
    }

    @Test
    void deveRetornarErroQuandoNomeForVazio() throws Exception {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        usuarioRequest.setNome("");

        final var requestJson = new ObjectMapper().writeValueAsString(usuarioRequest);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verify(cadastrarUsuarioUseCase, never()).execute(any(UsuarioRequest.class));
    }

    @Test
    void deveRetornarErroQuandoEmailForNulo() throws Exception {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        usuarioRequest.setEmail(null);

        final var requestJson = new ObjectMapper().writeValueAsString(usuarioRequest);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verify(cadastrarUsuarioUseCase, never()).execute(any(UsuarioRequest.class));
    }

    @Test
    void deveRetornarErroQuandoEmailForVazio() throws Exception {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        usuarioRequest.setEmail("");

        final var requestJson = new ObjectMapper().writeValueAsString(usuarioRequest);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());

        verify(cadastrarUsuarioUseCase, never()).execute(any(UsuarioRequest.class));
    }

    @Test
    void deveBuscarUsuarioPorEmailComSucesso() throws Exception {
        final var usuarioResponse = UsuarioTestUtils.buildUsuarioResponse();

        when(buscarUsuarioPorEmailUseCase.execute(any(String.class)))
                .thenReturn(usuarioResponse);

        mockMvc.perform(get("/usuario/findByEmail?email=teste@teste.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("nome").value("João"))
                .andExpect(jsonPath("email").value("teste@teste.com"));
    }

    @Test
    void deveAtualizarCadastroUsuarioComSucesso() throws Exception {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();
        final var usuarioResponse = UsuarioTestUtils.buildUsuarioResponse();

        when(atualizarCadastroUsuarioUseCase.execute(any(UsuarioRequest.class)))
                .thenReturn(usuarioResponse);

        final var requestJson = new ObjectMapper().writeValueAsString(usuarioRequest);

        mockMvc.perform(put("/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andDo(result -> {
                    final var content = result.getResponse().getContentAsString();
                    final var nome = usuarioResponse.getNome();
                    final var email = usuarioResponse.getEmail();
                    final var telefone = usuarioResponse.getTelefone();
                    final var id = usuarioResponse.getId();

                    assert content.contains(nome);
                    assert content.contains(email);
                    assert content.contains(telefone);
                    assert content.contains(String.valueOf(id));
                });

        verify(atualizarCadastroUsuarioUseCase, times(1)).execute(any(UsuarioRequest.class));
    }

    @Test
    void deveRetornarErroAoAtualizarUsuarior() throws Exception {
        final var usuarioRequest = UsuarioTestUtils.buildUsuarioRequest();

        when(atualizarCadastroUsuarioUseCase.execute(any(UsuarioRequest.class)))
                .thenThrow(new UsuarioNotFoundException());

        final var requestJson = new ObjectMapper().writeValueAsString(usuarioRequest);

        mockMvc.perform(put("/usuario/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());

        verify(atualizarCadastroUsuarioUseCase, times(1)).execute(any(UsuarioRequest.class));
    }


    @Test
    void deveDeletarUsuarioComSucesso() throws Exception {
        mockMvc.perform(delete("/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(deletarUsuarioUseCase, times(1)).execute(1L);
    }

    @Test
    void deveRetornarErroAoDeletarUsuario() throws Exception {
        doThrow(new UsuarioNotFoundException()).when(deletarUsuarioUseCase).execute(5L);

        mockMvc.perform(delete("/usuario/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(deletarUsuarioUseCase, times(1)).execute(5L);
    }
}
