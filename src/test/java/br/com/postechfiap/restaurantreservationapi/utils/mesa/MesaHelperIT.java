package br.com.postechfiap.restaurantreservationapi.utils.mesa;

import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.mesa.MesaIndisponivelException;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.MesaRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MesaHelperIT {

    @Autowired
    private MesaHelper mesaHelper;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ReservaHelper reservaHelper;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("findMesasByRestaurante1")
    public void deveRetornarMesasOrdenadasPorNumeroMesa() {
        // Arrange
        Long restauranteId = 1L;

        // Act
        List<Mesa> mesas = mesaHelper.findMesasByRestaurante(restauranteId);

        // Assert
        assertThat(mesas).hasSize(2);
        assertThat(mesas.get(0).getNumeroMesa()).isEqualTo(1);
        assertThat(mesas.get(1).getNumeroMesa()).isEqualTo(2);
    }

    @Test
    @DisplayName("findMesasByRestaurante2")
    public void deveRetornarConjuntoVazioQuandoRestauranteNaoTiverMesas() {
        // Arrange
        // Criar o restaurante (sem mesas associadas)
        Restaurante restaurante = Restaurante.builder().nome("Restaurante Legal").
                endereco(null).
                capacidade(10).
                tipoCozinha(TiposCozinhaEnum.AMAZONICA)
                .horarioFuncionamento("11:00 - 22:00").build();

        var restauranteSalvo = restauranteRepository.save(restaurante);


        // Act
        List<Mesa> mesas = mesaHelper.findMesasByRestaurante(restauranteSalvo.getId());

        // Assert
        assertThat(mesas).isEmpty();
    }


    @Test
    @DisplayName("findMesasReservadasByRestaurante1")
    public void deveRetornarMesasReservadasPorRestaurante() {
        // Arrange
        Long restauranteId = 1L;
        LocalDateTime dataHoraReserva = LocalDateTime.of(2025,3,5,19,30,0);

        // Act
        Set<Mesa> mesasReservadas = mesaHelper.findMesasReservadasByRestaurante(restauranteId, dataHoraReserva);

        // Assert
        assertThat(mesasReservadas).hasSize(1);
        assertThat(mesasReservadas.iterator().next().getNumeroMesa()).isEqualTo(1);
    }


    @Test
    @DisplayName("findMesasReservadasByRestaurante2")
    public void deveRetornarConjuntoVazioQuandoRestauranteNaoTiverMesasReservadas() {
        // Arrange
        LocalDateTime dataHoraReserva = LocalDateTime.now().plusMinutes(30); // Reserva para daqui a 30 minutos

        // Act
        Set<Mesa> mesasReservadas = mesaHelper.findMesasReservadasByRestaurante(1L, dataHoraReserva);

        // Assert
        assertThat(mesasReservadas).isEmpty();  // Espera-se que não haja mesas reservadas nesse restaurante
    }

    @Test
    @DisplayName("calculaMesasDisponiveisByRestaurante1")
    public void deveRetornarMesasDisponiveisQuandoSuficientes() {
        // Arrange
        LocalDateTime dataHoraReserva = LocalDateTime.of(2024, 3, 5, 19, 30, 0);

        // Não há reservas para o horário, então todas as mesas devem estar disponíveis

        // Act
        List<Mesa> mesasDisponiveis = mesaHelper.calculaMesasDisponiveisByRestaurante
                (1L, 3, dataHoraReserva);

        // Assert
        assertThat(mesasDisponiveis).hasSize(2); // Espera-se que o número mínimo de mesas necessárias seja 2
    }

    @Test
    @DisplayName("calculaMesasDisponiveisByRestaurante2")
    public void deveLancarExcecaoQuandoNaoHouverMesasSuficientes() {
        // Arrange
        Long restauranteId = 1L;
        int numeroDePessoas = 10; // Exemplo de número maior de pessoas
        LocalDateTime dataHoraReserva = LocalDateTime.of(2025, 3, 5, 19, 30, 0);

        // Act & Assert
        assertThatThrownBy(() -> mesaHelper.calculaMesasDisponiveisByRestaurante
                (restauranteId, numeroDePessoas, dataHoraReserva))
                .isInstanceOf(MesaIndisponivelException.class)
                .hasMessageContaining("Não há mesas suficientes para acomodar 10 pessoas");
    }

    @Test
    @DisplayName("calculaMesasDisponiveisByRestaurante3")
    public void deveRetornarMesasDisponiveisQuandoNaoHouverReservas() {
        // Arrange
        Long restauranteId = 1L;
        int numeroDePessoas = 4;
        LocalDateTime dataHoraReserva = LocalDateTime.of(2025, 4, 5, 19, 30, 0);

        // Não há reservas para o horário, então todas as mesas devem estar disponíveis

        // Act
        List<Mesa> mesasDisponiveis = mesaHelper.calculaMesasDisponiveisByRestaurante
                (restauranteId, numeroDePessoas, dataHoraReserva);

        // Assert
        assertThat(mesasDisponiveis).hasSize(2); // Espera-se que todas as mesas estejam disponíveis
    }

    @Test
    @DisplayName("calculaMesasDisponiveisByRestaurante4")
    public void deveRetornarApenasMesasNaoReservadasQuandoExistiremReservas() {
        // Arrange
        Long restauranteId = 1L;
        int numeroDePessoas = 2;
        LocalDateTime dataHoraReserva = LocalDateTime.of(2025, 3, 5, 19, 30, 0);


        // Act
        List<Mesa> mesasDisponiveis = mesaHelper.calculaMesasDisponiveisByRestaurante
                (restauranteId, numeroDePessoas, dataHoraReserva);

        // Assert
        assertThat(mesasDisponiveis).hasSize(1);
        assertThat(mesasDisponiveis.get(0).getNumeroMesa()).isEqualTo(2);
    }
}
