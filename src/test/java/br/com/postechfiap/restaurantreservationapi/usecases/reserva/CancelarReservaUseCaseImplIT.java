package br.com.postechfiap.restaurantreservationapi.usecases.reserva;

import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CancelarReservaUseCaseImplIT {

    @Autowired
    private CancelarReservaUseCaseImpl cancelarReservaUseCase;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaHelper reservaHelper;


    @BeforeEach
    public void setUp() {
    }

    @Test
    public void deveCancelarReservaComSucesso() {

        String mensagemRetorno = cancelarReservaUseCase.execute(3L);

        // Verifica se a reserva foi deletada do banco
        Optional<Reserva> reservaDeletada = reservaRepository.findById(3L);

        // Verifica se o retorno está correto e se a reserva foi realmente deletada
        assertThat(mensagemRetorno).isEqualTo("Reserva com ID " + 3 + " foi deletada com sucesso.");
        assertThat(reservaDeletada).isNotPresent(); // A reserva não deve estar presente no banco
    }

    @Test
    public void deveRetornarErroSeReservaNaoExistir() {
        // Arrange
        Long idInexistente = 999L; // Supondo que esse ID não exista no banco

        // Act & Assert
        ReservaNotFoundException exception = assertThrows(ReservaNotFoundException.class, () -> {
            cancelarReservaUseCase.execute(idInexistente);
        });

        // Verifica se a mensagem da exceção está correta
        assertThat(exception.getMessage()).isEqualTo("Reserva não encontrada.");
    }

}
