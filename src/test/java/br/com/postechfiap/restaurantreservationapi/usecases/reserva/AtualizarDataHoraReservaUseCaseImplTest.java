package br.com.postechfiap.restaurantreservationapi.usecases.reserva;


import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.gerenciamento.ReservaAtualizarDataHoraRequest;
import br.com.postechfiap.restaurantreservationapi.entities.*;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.mesa.MesaIndisponivelException;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarDataHoraReservaUseCaseImplTest {

    @InjectMocks
    private AtualizarDataHoraReservaUseCaseImpl atualizarDataHoraReservaUseCase;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ReservaHelper reservaHelper;

    @Mock
    private MesaHelper mesaHelper;

    @Mock
    private Restaurante restaurante;

    @Mock
    private Endereco endereco;

    @Mock
    private Usuario usuario;

    // Mocks para as mesas
    @Mock
    private Mesa mesa1;

    @Mock
    private Mesa mesa2;

    @Mock
    private Mesa mesa3;

    @Mock
    private Mesa mesa4;

    // Dados necessários para os testes
    private ReservaAtualizarDataHoraRequest request;
    private List<Mesa> mesas;
    private List<Mesa> novasMesasDisponiveis;

    @BeforeEach
    void setUp() {
        // Configuração inicial dos mocks
        request = new ReservaAtualizarDataHoraRequest(
                1L, LocalDateTime.of(2024, 1, 5, 20, 0));

        // As mesas agora são mocks
        mesa1 = mock(Mesa.class);
        mesa2 = mock(Mesa.class);
        mesa3 = mock(Mesa.class);
        mesa4 = mock(Mesa.class);

        // Listas de mesas
        mesas = new ArrayList<>(List.of(mesa1, mesa2));
        novasMesasDisponiveis = new ArrayList<>(List.of(mesa3, mesa4));

    }

    @Test
    void deveAtualizarReservaComSucesso() {
        // Arrange
        Reserva reserva = new Reserva(
                1L,
                usuario,
                restaurante,
                mesas,
                LocalDateTime.of(2024, 1, 4, 19, 0),
                4
        );

        // Mocks
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(mesaHelper.calculaMesasDisponiveisByRestaurante(
                reserva.getRestaurante().getId(),
                reserva.getNumeroDePessoas(),
                request.getDataAlteracao()
        )).thenReturn(novasMesasDisponiveis);

        when(reservaRepository.save(reserva)).thenReturn(reserva);

        // Act
        ReservaResponse response = atualizarDataHoraReservaUseCase.execute(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getReservaId()).isEqualTo(1L);
        assertThat(response.getDataHoraReserva()).isEqualTo(request.getDataAlteracao());

        // Comparar as listas de IDs
        List<String> idsEsperados = List.of("001-003", "001-004");
        assertThat(response.getMesas()).isEqualTo(idsEsperados);  // Comparação direta entre listas de IDs
        verify(reservaRepository).save(reserva);  // Verifica se o save foi chamado
    }


    @Test
    void deveLancarExcecao_SeReservaNaoExistir() {
        // Arrange
        when(reservaHelper.getReservaById(1L)).thenThrow(new ReservaNotFoundException());

        // Act & Assert
        assertThatThrownBy(() -> atualizarDataHoraReservaUseCase.execute(request))
                .isInstanceOf(ReservaNotFoundException.class)
                .hasMessageContaining("Reserva não encontrada");
    }

    @Test
    void deveLancarExcecao_SeNaoHouverMesasDisponiveis() {
        // Arrange

        Restaurante restaurante1 = new Restaurante(
                1L,
                "Restaurante Teste",
                endereco,
                TiposCozinhaEnum.AMAZONICA,
                "11:00 - 23:00",
                100);

        Reserva reserva = new Reserva(
                1L,
                usuario,
                restaurante1,
                mesas,
                LocalDateTime.of(2024, 1, 4, 19, 0),
                10
        );

        when(reservaHelper.getReservaById(1L)).thenReturn(reserva);

        when(mesaHelper.calculaMesasDisponiveisByRestaurante(
                reserva.getRestaurante().getId(),
                reserva.getNumeroDePessoas(),
                request.getDataAlteracao()
        )).thenThrow(new MesaIndisponivelException(
                "Não há mesas suficientes para acomodar " + reserva.getNumeroDePessoas() + " pessoas.")
        );

        // Assert
        assertThatThrownBy(() -> atualizarDataHoraReservaUseCase.execute(request))
                .isInstanceOf(MesaIndisponivelException.class)
                .hasMessageContaining(
                        "Não há mesas suficientes para acomodar "
                                + reserva.getNumeroDePessoas() + " pessoas.");
    }
}
