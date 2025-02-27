package br.com.postechfiap.restaurantreservationapi.usecases.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.entities.*;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteHelper;
import br.com.postechfiap.restaurantreservationapi.utils.UsuarioHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservarMesaUseCaseImplTest {

    @InjectMocks
    private ReservarMesaUseCaseImpl reservarMesaUseCase;

    @Mock
    private RestauranteHelper restauranteHelper;

    @Mock
    private UsuarioHelper usuarioHelper;

    @Mock
    private MesaHelper mesaHelper;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private Restaurante restaurante;

    @Mock
    private Usuario usuario;

    @Mock
    private Mesa mesa1;

    @Mock
    private Mesa mesa2;

    private List<Mesa> mesasDisponiveis;
    private ReservaRequest request;

    @BeforeEach
    void setUp() {
        // Configuração de objetos repetidos
        mesasDisponiveis = List.of(mesa1, mesa2);

        request = new ReservaRequest(
                2L,
                1L,
                LocalDateTime.of(2024, 1, 4, 19, 0), 4);
    }

    @Test
    void deveReservarMesaComSucesso() {
        // Arrange
        Reserva reservaMock = new Reserva(
                1L, usuario, restaurante, mesasDisponiveis,
                request.getDataHoraReserva(), 4);

        when(restauranteHelper.validateRestauranteExists(1L)).thenReturn(restaurante);
        when(usuarioHelper.validateUsuarioExists(2L)).thenReturn(usuario);
        when(mesaHelper.calculaMesasDisponiveisByRestaurante(1L,
                4,
                request.getDataHoraReserva()))
                .thenReturn(mesasDisponiveis);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaMock);

        // Configura o mock para retornar o id 2L quando getId() for chamado
        when(usuario.getId()).thenReturn(2L);

        // Act
        ReservaResponse response = reservarMesaUseCase.execute(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getReservaId()).isEqualTo(1L);
        assertThat(response.getUsuarioId()).isEqualTo(2L);
        assertThat(response.getRestauranteName()).isEqualTo(restaurante.getNome()); // Pega o nome mockado
        assertThat(response.getNumeroDePessoas()).isEqualTo(4);
    }

    @Test
    void deveLancarExcecao_SeRestauranteNaoExistir() {
        // Arrange
        when(restauranteHelper.validateRestauranteExists(1L))
                .thenThrow(new RuntimeException("Restaurante não encontrado"));

        // Act & Assert
        assertThatThrownBy(() -> reservarMesaUseCase.execute(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Restaurante não encontrado");
    }

    @Test
    void deveLancarExcecao_SeUsuarioNaoExistir() {
        // Arrange
        when(restauranteHelper.validateRestauranteExists(1L)).thenReturn(restaurante);
        when(usuarioHelper.validateUsuarioExists(2L)).thenThrow(new RuntimeException("Usuário não encontrado"));

        // Act & Assert
        assertThatThrownBy(() -> reservarMesaUseCase.execute(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuário não encontrado");
    }

    @Test
    void deveLancarExcecao_SeNaoHouverMesasDisponiveis() {
        // Arrange
        when(restauranteHelper.validateRestauranteExists(1L)).thenReturn(restaurante);
        when(usuarioHelper.validateUsuarioExists(2L)).thenReturn(usuario);
        when(mesaHelper.calculaMesasDisponiveisByRestaurante(
                1L,
                4, request.getDataHoraReserva()))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> reservarMesaUseCase.execute(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Não há mesas disponíveis");
    }
}
