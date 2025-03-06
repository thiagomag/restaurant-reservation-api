package br.com.postechfiap.restaurantreservationapi.validator;

import br.com.postechfiap.restaurantreservationapi.entities.Avaliacao;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.enuns.NotaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaJaAvaliadaException;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AvaliacaoValidatorIT {

    @Autowired
    private AvaliacaoValidator avaliacaoValidator;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    @DisplayName("validarNota - Nota dentro do intervalo válido")
    public void deveAceitarNotaValida() {
        // Act & Assert
        assertDoesNotThrow(() -> avaliacaoValidator.validarNota(3));
    }

    @Test
    @DisplayName("validarNota - Nota abaixo do intervalo")
    public void deveLancarExcecaoQuandoNotaForMenorQue1() {
        // Act & Assert
        assertThatThrownBy(() -> avaliacaoValidator.validarNota(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A nota deve ser entre 1 e 5.");
    }

    @Test
    @DisplayName("validarNota - Nota acima do intervalo")
    public void deveLancarExcecaoQuandoNotaForMaiorQue5() {
        // Act & Assert
        assertThatThrownBy(() -> avaliacaoValidator.validarNota(6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A nota deve ser entre 1 e 5.");
    }

    @Test
    @DisplayName("validarSeReservaJaFoiAvaliada - Reserva não foi avaliada")
    public void deveAceitarReservaNaoAvaliada() {
        // Act & Assert
        assertDoesNotThrow(() -> avaliacaoValidator.validarSeReservaJaFoiAvaliada(1L));
    }

    @Test
    @DisplayName("validarSeReservaJaFoiAvaliada - Reserva já foi avaliada")
    public void deveLancarExcecaoQuandoReservaJaFoiAvaliada() {
        // Arranjo: Criar uma avaliação para a reserva
        Reserva reserva = reservaRepository.findById(1L).orElseThrow();

        Avaliacao avaliacao = new Avaliacao(reserva, NotaEnum.EXCELENTE,"Ótimo restaurante");

        avaliacaoRepository.save(avaliacao);

        // Act & Assert
        assertThatThrownBy(() -> avaliacaoValidator.validarSeReservaJaFoiAvaliada(1L))
                .isInstanceOf(ReservaJaAvaliadaException.class)
                .hasMessageContaining("Reserva 1 já foi avaliada.");
    }
}
