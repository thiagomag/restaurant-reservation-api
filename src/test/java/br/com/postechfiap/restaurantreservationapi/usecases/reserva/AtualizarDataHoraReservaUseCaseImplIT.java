package br.com.postechfiap.restaurantreservationapi.usecases.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.gerenciamento.ReservaAtualizarDataHoraRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.exception.mesa.MesaIndisponivelException;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AtualizarDataHoraReservaUseCaseImplIT {

    @Autowired
    private AtualizarDataHoraReservaUseCaseImpl atualizarDataHoraReservaUseCase;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaHelper reservaHelper;

    @Mock
    private MesaHelper mesaHelper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    @Transactional
    public void deveAtualizarDataHoraReservaComSucesso() {
        // ARRANGE

        // Nova data/hora
        LocalDateTime novaDataHora = LocalDateTime.of(2025, 5, 15, 20, 0);

        ReservaAtualizarDataHoraRequest request = ReservaAtualizarDataHoraRequest.builder()
                .reservaId(1L)
                .dataAlteracao(novaDataHora)
                .build();

        // ACT - Atualizar a reserva
        ReservaResponse response = atualizarDataHoraReservaUseCase.execute(request);

        // ASSERT - Validando a atualização
        Reserva reservaAtualizada = reservaRepository.findById(1L).orElseThrow();


        assertNotNull(response);
        assertEquals(novaDataHora, response.getDataHoraReserva());
        assertEquals(reservaAtualizada.getUsuario().getId(), response.getUsuarioId());
        assertEquals(reservaAtualizada.getRestaurante().getNome(), response.getRestauranteName());
        assertEquals(1, response.getMesas().size()); // Verifica se as mesas foram realocadas corretamente

        // Validando persistência no banco
        assertEquals(novaDataHora, reservaAtualizada.getDataHoraReserva());
        assertEquals(1, reservaAtualizada.getMesas().size());
    }

    @Test
    @Transactional
    public void deveRetornarErroQuandoReservaNaoExistir() {
        // ARRANGE - Criando um ID de reserva inexistente
        Long reservaInexistenteId = 999L;
        LocalDateTime novaDataHora = LocalDateTime.of(2025, 5, 15, 20, 0);

        ReservaAtualizarDataHoraRequest request = ReservaAtualizarDataHoraRequest.builder()
                .reservaId(reservaInexistenteId)
                .dataAlteracao(novaDataHora)
                .build();

        // ACT & ASSERT - Esperar a exceção ReservaNotFoundException
        Exception exception = assertThrows(ReservaNotFoundException.class, () -> {
            atualizarDataHoraReservaUseCase.execute(request);
        });

        assertNotNull(exception);
        assertEquals("Reserva não encontrada.", exception.getMessage());
    }


}
