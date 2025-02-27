package br.com.postechfiap.restaurantreservationapi.usecases.reserva;

import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelarReservaUseCaseImplTest {

    @InjectMocks
    private CancelarReservaUseCaseImpl cancelarReservaUseCase;

    @Mock
    private ReservaHelper reservaHelper;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private Reserva reserva;

    @Mock
    private Restaurante restaurante;

    @Mock
    private Usuario usuario;

    @Mock
    private List<Mesa> mesas;

    @BeforeEach
    void setUp() {
        // Criando a reserva com dados mais completos e realistas
        reserva = new Reserva(
                1L,
                usuario,
                restaurante,
                mesas,
                LocalDateTime.now(),
                4);

        // Configuração de mocks adicionais, se necessário
        when(reservaHelper.getReservaById(1L)).thenReturn(reserva);
    }

    @Test
    void deveCancelarReservaComSucesso() {
        // Arrange
        // A reserva foi configurada no @BeforeEach

        // Act
        String response = cancelarReservaUseCase.execute(1L);

        // Assert
        assertThat(response).isEqualTo("Reserva com ID 1 foi deletada com sucesso.");
        verify(reservaRepository).deleteById(1L); // Verifica se o delete foi chamado no repositório
    }

    @Test
    void deveLancarExcecao_SeReservaNaoExistir() {
        // Arrange
        when(reservaHelper.getReservaById(1L)).thenThrow(new RuntimeException("Reserva não encontrada"));

        // Act & Assert
        assertThatThrownBy(() -> cancelarReservaUseCase.execute(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Reserva não encontrada");

        verify(reservaRepository, never()).deleteById(1L); // Verifica que o delete não foi chamado
    }
}