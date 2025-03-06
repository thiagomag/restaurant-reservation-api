package br.com.postechfiap.restaurantreservationapi.usecases.reserva;

import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.reserva.ReservaResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;

import br.com.postechfiap.restaurantreservationapi.exception.mesa.MesaIndisponivelException;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.exception.usuario.UsuarioNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteHelper;
import br.com.postechfiap.restaurantreservationapi.validator.UsuarioValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ReservarMesaUseCaseImplIT {

    @Autowired
    private ReservarMesaUseCaseImpl reservarMesaUseCase;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioValidator usuarioValidator;

    @Autowired
    private RestauranteHelper restauranteHelper;

    @Autowired
    private MesaHelper mesaHelper;

    @BeforeEach
    void setup () {
    }

    @Test
    @Transactional
    public void deveReservarMesaComSucesso() {
        // ARRANGE - Criando um cenário válido
        LocalDateTime dataHoraReserva = LocalDateTime.of(2025, 5, 10, 19, 30);

        ReservaRequest reservaRequest = ReservaRequest.builder()
                .restauranteId(1L)
                .usuarioId(1L)
                .numeroDePessoas(4)
                .dataHoraReserva(dataHoraReserva)
                .build();

        // ACT - Executando o caso de uso
        ReservaResponse response = reservarMesaUseCase.execute(reservaRequest);

        // ASSERT - Validando a resposta
        System.out.println("Quantidade de mesas reservadas: " + response.getMesas().size());
        assertNotNull(response);
        assertEquals("Restaurante Sabor", response.getRestauranteName());
        assertEquals(1L, response.getUsuarioId());
        assertEquals(4, response.getNumeroDePessoas());
        assertEquals(dataHoraReserva, response.getDataHoraReserva());
        assertEquals(2, response.getMesas().size()); // Devem ser 2 mesas reservadas

        // Verificando a persistência no banco
        List<Reserva> reservasNoBanco = reservaRepository.findAll();
        System.out.println("Quantidade de reservas no banco: " + reservasNoBanco.size());
        assertEquals(4, reservasNoBanco.size()); // Deve haver apenas 1 reserva

        Reserva reservaSalva = reservasNoBanco.get(3);
        assertEquals(2, reservaSalva.getMesas().size()); // Confere se as mesas foram associadas
        assertEquals(1L, reservaSalva.getUsuario().getId());
        assertEquals("Restaurante Sabor", reservaSalva.getRestaurante().getNome());
    }

    @Test
    @Transactional
    public void deveRetornarErroQuandoNumeroDePessoasForMaiorQueMesasDisponiveis() {
        // ARRANGE - Criando um cenário com número de pessoas maior que a capacidade do restaurante
        LocalDateTime dataHoraReserva = LocalDateTime.of(2025, 5, 10, 19, 30);

        // Um número de pessoas muito grande
        ReservaRequest reservaRequest = ReservaRequest.builder()
                .restauranteId(1L)
                .usuarioId(1L)
                .numeroDePessoas(15)  // Número maior do que a capacidade total disponível
                .dataHoraReserva(dataHoraReserva)
                .build();

        // ACT - Executando o caso de uso
        Exception exception = assertThrows(MesaIndisponivelException.class, () -> {
            reservarMesaUseCase.execute(reservaRequest);
        });

        // ASSERT - Verificando se a exceção foi lançada
        assertNotNull(exception);
        assertEquals("Não há mesas suficientes para acomodar 15 pessoas.", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveRetornarErroQuandoRestauranteNaoExistir() {
        // ARRANGE
        LocalDateTime dataHoraReserva = LocalDateTime.of(2025, 5, 10, 19, 30);

        ReservaRequest reservaRequest = ReservaRequest.builder()
                .restauranteId(999L) // Restaurante inexistente
                .usuarioId(1L)
                .numeroDePessoas(4)
                .dataHoraReserva(dataHoraReserva)
                .build();

        // ACT & ASSERT
        Exception exception = assertThrows(RestauranteNotFoundException.class, () -> {
            reservarMesaUseCase.execute(reservaRequest);
        });

        assertNotNull(exception);
        assertEquals("Restaurante não encontrado.", exception.getMessage());
    }

    @Test
    @Transactional
    public void deveRetornarErroQuandoUsuarioNaoExistir() {
        // ARRANGE
        LocalDateTime dataHoraReserva = LocalDateTime.of(2025, 5, 10, 19, 30);

        ReservaRequest reservaRequest = ReservaRequest.builder()
                .restauranteId(1L)
                .usuarioId(999L) // Usuário inexistente
                .numeroDePessoas(4)
                .dataHoraReserva(dataHoraReserva)
                .build();

        // ACT & ASSERT
        Exception exception = assertThrows(UsuarioNotFoundException.class, () -> {
            reservarMesaUseCase.execute(reservaRequest);
        });

        assertNotNull(exception);
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

}