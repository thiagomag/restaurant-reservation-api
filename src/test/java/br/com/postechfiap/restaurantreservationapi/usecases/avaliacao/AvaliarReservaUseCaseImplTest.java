package br.com.postechfiap.restaurantreservationapi.usecases.avaliacao;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Avaliacao;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.enuns.NotaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import br.com.postechfiap.restaurantreservationapi.validator.AvaliacaoValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class AvaliarReservaUseCaseImplTest {

    @InjectMocks
    private AvaliarReservaUseCaseImpl avaliarReservaUseCase;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private AvaliacaoValidator avaliacaoValidator;

    @Mock
    private ReservaHelper reservaHelper;

    @Mock
    private Reserva reserva;

    @Mock
    private Usuario usuario;

    @Mock
    private Restaurante restaurante;

    private AvaliacaoRequest request;

    @BeforeEach
    void setUp() {
        // Configuração de objetos repetidos
        request = new AvaliacaoRequest(1L, 5, "Muito boa a refeição.");
    }

    @Test
    void deveAvaliarReservaComSucesso() {
        // Arrange
        Avaliacao avaliacaoMock = new Avaliacao(
                reserva,
                NotaEnum.EXCELENTE,
                request.getComentario()
        );

        // Usando doNothing para simular que a validação da nota passa
        doNothing().when(avaliacaoValidator).validarNota(request.getNota());
        // Usando doNothing para simular que a reserva ainda não foi avaliada
        doNothing().when(avaliacaoValidator).validarSeReservaJaFoiAvaliada(request.getReservaId());

        when(reservaHelper.getReservaById(request.getReservaId())).thenReturn(reserva); // Simula a busca pela reserva
        when(avaliacaoRepository.save(any(Avaliacao.class))).thenReturn(avaliacaoMock); // Simula o salvamento da avaliação

        when(reserva.getId()).thenReturn(1L);
        when(reserva.getUsuario()).thenReturn(usuario);
        when(usuario.getId()).thenReturn(2L);
        when(reserva.getRestaurante()).thenReturn(restaurante);
        when(restaurante.getNome()).thenReturn("Restaurante Exemplo");

        // Act
        AvaliacaoResponse response = avaliarReservaUseCase.execute(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getReservaId()).isEqualTo(1L);
        assertThat(response.getUsuarioId()).isEqualTo(2L);
        assertThat(response.getRestauranteName()).isEqualTo("Restaurante Exemplo");
        assertThat(response.getNota()).isEqualTo(5);
        assertThat(response.getComentario()).isEqualTo("Muito boa a refeição.");
    }

    @Test
    void deveLancarExcecao_SeNotaInvalida() {
        // Arrange
        doThrow(new IllegalArgumentException("Nota inválida")).when(avaliacaoValidator).validarNota(request.getNota());

        // Act & Assert
        assertThatThrownBy(() -> avaliarReservaUseCase.execute(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Nota inválida");
    }

    @Test
    void deveLancarExcecao_SeReservaJaFoiAvaliada() {
        // Arrange
        doNothing().when(avaliacaoValidator).validarNota(request.getNota());
        doThrow(new RuntimeException("Reserva já foi avaliada")).when(avaliacaoValidator).validarSeReservaJaFoiAvaliada(request.getReservaId());

        // Act & Assert
        assertThatThrownBy(() -> avaliarReservaUseCase.execute(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Reserva já foi avaliada");
    }

    @Test
    void deveLancarExcecao_SeReservaNaoExistir() {
        // Arrange
        doNothing().when(avaliacaoValidator).validarNota(request.getNota());
        doNothing().when(avaliacaoValidator).validarSeReservaJaFoiAvaliada(request.getReservaId());
        when(reservaHelper.getReservaById(request.getReservaId())).thenThrow(new ReservaNotFoundException());

        // Act & Assert
        assertThatThrownBy(() -> avaliarReservaUseCase.execute(request))
                .isInstanceOf(ReservaNotFoundException.class)
                .hasMessageContaining("Reserva não encontrada");
    }
}