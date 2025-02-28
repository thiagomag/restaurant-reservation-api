package br.com.postechfiap.restaurantreservationapi.validator;

import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaJaAvaliadaException;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvaliacaoValidatorTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @InjectMocks
    private AvaliacaoValidator avaliacaoValidator;

    @Test
    void deveLancarExcecao_SeNotaForInvalida() {
        // Testando nota nula
        assertThatThrownBy(() -> avaliacaoValidator.validarNota(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A nota deve ser entre 1 e 5.");

        // Testando nota abaixo de 1
        assertThatThrownBy(() -> avaliacaoValidator.validarNota(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A nota deve ser entre 1 e 5.");

        // Testando nota acima de 5
        assertThatThrownBy(() -> avaliacaoValidator.validarNota(6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A nota deve ser entre 1 e 5.");
    }

    @Test
    void naoDeveLancarExcecao_SeNotaForValida() {
        // Testando nota válida
        assertThatCode(() -> avaliacaoValidator.validarNota(1)).doesNotThrowAnyException();
        assertThatCode(() -> avaliacaoValidator.validarNota(5)).doesNotThrowAnyException();
    }

    @Test
    void deveLancarExcecao_SeReservaJaFoiAvaliada() {
        // Arrange
        Long reservaId = 1L;
        when(avaliacaoRepository.existsByReservaId(reservaId)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> avaliacaoValidator.validarSeReservaJaFoiAvaliada(reservaId))
                .isInstanceOf(ReservaJaAvaliadaException.class)
                .hasMessageContaining("Reserva " + reservaId + " já foi avaliada.");
    }

    @Test
    void naoDeveLancarExcecao_SeReservaNaoFoiAvaliada() {
        // Arrange
        Long reservaId = 1L;
        when(avaliacaoRepository.existsByReservaId(reservaId)).thenReturn(false);

        // Act & Assert
        assertThatCode(() -> avaliacaoValidator.validarSeReservaJaFoiAvaliada(reservaId))
                .doesNotThrowAnyException();
    }






}
