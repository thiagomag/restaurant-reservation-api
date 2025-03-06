package br.com.postechfiap.restaurantreservationapi.utils.reserva;

import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReservaHelperIT {

    @Autowired
    private ReservaHelper reservaHelper;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    @DisplayName("getReservaById")
    public void deveRetornarReservaQuandoExistir() {
        // Arrange
        Long reservaId = 1L;

        // Act
        Reserva reserva = reservaHelper.getReservaById(reservaId);

        // Assert
        assertThat(reserva).isNotNull();
        assertThat(reserva.getId()).isEqualTo(reservaId);
    }

    @Test
    @DisplayName("getReservaById - Reserva não encontrada")
    public void deveLancarExcecaoQuandoReservaNaoExistir() {
        // Arrange
        Long reservaIdInexistente = 999L;

        // Act & Assert
        assertThatThrownBy(() -> reservaHelper.getReservaById(reservaIdInexistente))
                .isInstanceOf(ReservaNotFoundException.class)
                .hasMessageContaining("Reserva não encontrada.");
    }

    @Test
    @DisplayName("getReservaByHoraMarcada - Com reservas no intervalo")
    public void deveRetornarReservasQuandoExistiremNoIntervalo() {
        // Arrange
        Long restauranteId = 1L;
        LocalDateTime inicioIntervalo = LocalDateTime.of(2025, 3, 5, 19, 0, 0);
        LocalDateTime fimIntervalo = LocalDateTime.of(2025, 3, 6, 21, 0, 0);

        // Act
        List<Reserva> reservas = reservaHelper.getReservaByHoraMarcada(restauranteId, inicioIntervalo, fimIntervalo);

        System.out.println(reservas);

        // Assert
        assertThat(reservas).hasSize(1);
    }

    @Test
    @DisplayName("getReservaByHoraMarcada - Sem reservas no intervalo")
    public void deveLancarExcecaoQuandoNaoExistiremReservasNoIntervalo() {
        // Arrange
        Long restauranteId = 1L;
        LocalDateTime inicioIntervalo = LocalDateTime.of(2025, 3, 6, 22, 0, 0);
        LocalDateTime fimIntervalo = LocalDateTime.of(2025, 3, 7, 23, 0, 0);

        // Act & Assert
        assertThatThrownBy(() -> reservaHelper.getReservaByHoraMarcada(restauranteId, inicioIntervalo, fimIntervalo))
                .isInstanceOf(ReservaNotFoundException.class)
                .hasMessageContaining("Reserva não encontrada.");
    }

    @Test
    @DisplayName("getReservaByHoraMarcada - Intervalo vazio")
    public void deveLancarExcecaoQuandoIntervaloVazio() {
        // Arrange
        Long restauranteId = 1L;
        LocalDateTime inicioIntervalo = LocalDateTime.now();
        LocalDateTime fimIntervalo = inicioIntervalo.minusMinutes(30); // Intervalo inválido (fim antes de início)

        // Act & Assert
        assertThatThrownBy(() -> reservaHelper.getReservaByHoraMarcada(restauranteId, inicioIntervalo, fimIntervalo))
                .isInstanceOf(ReservaNotFoundException.class)
                .hasMessageContaining("Reserva não encontrada.");
    }

}