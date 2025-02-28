package br.com.postechfiap.restaurantreservationapi.utils.reserva;

import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReservaHelperTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaHelper reservaHelper;

    @BeforeEach
    public void setup() {
    }


    @Test
    void testGetReservaById_reservaExiste() {
        // Arrange
        Long reservaId = 1L;
        Reserva reserva = Reserva.builder().id(reservaId).build();

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

        // Act
        Reserva result = reservaHelper.getReservaById(reservaId);

        // Assert
        assertNotNull(result);
        assertEquals(reservaId, result.getId());
    }

    @Test
    void testGetReservaById_reservaNaoExiste() {
        // Arrange
        Long reservaId = 1L;

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> reservaHelper.getReservaById(reservaId))
                .isInstanceOf(ReservaNotFoundException.class)
                .hasMessage("Reserva não encontrada.");

    }

    @Test
    void testGetReservaByHoraMarcada_retornaReservas() {
        // Arrange
        Long restauranteId = 1L;
        LocalDateTime inicio = LocalDateTime.of(2024, 3, 1, 12, 0);
        LocalDateTime fim = LocalDateTime.of(2024, 3, 1, 14, 0);

        List<Reserva> reservasEsperadas = List.of(
                Reserva.builder().id(1L).build(),
                Reserva.builder().id(2L).build()
        );

        when(reservaRepository.findByRestauranteIdAndDataHoraReservaBetween(
                restauranteId, inicio, fim)).thenReturn(reservasEsperadas);

        // Act
        List<Reserva> result = reservaHelper.getReservaByHoraMarcada(restauranteId, inicio, fim);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetReservaByHoraMarcada_semReservas() {
        // Arrange
        Long restauranteId = 1L;
        LocalDateTime inicio = LocalDateTime.of(2024, 3, 1, 12, 0);
        LocalDateTime fim = LocalDateTime.of(2024, 3, 1, 14, 0);

        when(reservaRepository.findByRestauranteIdAndDataHoraReservaBetween(
                restauranteId, inicio, fim)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> reservaHelper.getReservaByHoraMarcada(restauranteId, inicio, fim))
                .isInstanceOf(ReservaNotFoundException.class)
                .hasMessage("Reserva não encontrada.");
    }

}
