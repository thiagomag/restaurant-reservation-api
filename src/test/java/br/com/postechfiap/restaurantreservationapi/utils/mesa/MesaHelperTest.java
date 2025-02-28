package br.com.postechfiap.restaurantreservationapi.utils.mesa;

import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.exception.mesa.MesaIndisponivelException;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.MesaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MesaHelperTest {

    @InjectMocks
    private MesaHelper mesaHelper;

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private ReservaHelper reservaHelper;

    @Mock
    private Restaurante restaurante;

    @BeforeEach
    public void setup() {

    }


    @Test
    void testObterProximoNumeroMesa_comMesasExistentes() {
        // Mock para retornar uma mesa com número 5
        Mesa mesa = Mesa.builder().id("001-005").restaurante(restaurante).numeroMesa(5).build();

        when(mesaRepository.findTopByRestauranteIdOrderByNumeroMesaDesc(1L))
                .thenReturn(Optional.of(mesa));

        // Act
        Integer proximoNumeroMesa = mesaHelper.obterProximoNumeroMesa(1L);

        // Assert
        assertEquals(6, proximoNumeroMesa);
    }

    @Test
    void testObterProximoNumeroMesa_semMesas() {
        // Arrange
        when(mesaRepository.findTopByRestauranteIdOrderByNumeroMesaDesc(1L))
                .thenReturn(Optional.empty());

        // Act
        Integer proximoNumeroMesa = mesaHelper.obterProximoNumeroMesa(1L);

        // Assert
        assertEquals(1, proximoNumeroMesa);
    }

    @Test
    void testGerarIdentificadorMesa() {

        // Arrange
        Long restauranteId = 1L;
        Integer numeroMesa = 5;

        // Act
        String identificadorMesa = mesaHelper.gerarIdentificadorMesa(restauranteId, numeroMesa);

        // Assert
        assertEquals("001-005", identificadorMesa);
    }

    @Test
    void testGerarIdentificadorMesa_comValoresMaximos() {

        // Arrange
        Long restauranteId = 999L;
        Integer numeroMesa = 999;

        // Act
        String identificadorMesa = mesaHelper.gerarIdentificadorMesa(restauranteId, numeroMesa);

        // Assert
        assertEquals("999-999", identificadorMesa);
    }


    @Test
    void testFindMesasByRestaurante_comMesas() {

        // Arrange: Simula o retorno do repositório com 3 mesas
        Mesa mesa1 = Mesa.builder().id("001-003").restaurante(restaurante).numeroMesa(3).build();
        Mesa mesa2 = Mesa.builder().id("001-001").restaurante(restaurante).numeroMesa(1).build();
        Mesa mesa3 = Mesa.builder().id("001-002").restaurante(restaurante).numeroMesa(2).build();

        when(mesaRepository.findByRestauranteId(1L)).thenReturn(Arrays.asList(mesa1, mesa2, mesa3));

        // Act: Chama o método
        List<Mesa> mesas = mesaHelper.findMesasByRestaurante(1L);

        // Assert: Verifica se as mesas foram ordenadas corretamente
        assertEquals(3, mesas.size());
        assertEquals(1, mesas.get(0).getNumeroMesa());
        assertEquals(2, mesas.get(1).getNumeroMesa());
        assertEquals(3, mesas.get(2).getNumeroMesa());
    }

    @Test
    void testFindMesasByRestaurante_semMesas() {
        // Arrange: Simula o retorno de uma lista vazia
        when(mesaRepository.findByRestauranteId(1L)).thenReturn(Collections.emptyList());

        // Act: Chama o método
        List<Mesa> mesas = mesaHelper.findMesasByRestaurante(1L);

        // Assert: Verifica se a lista está vazia
        assertTrue(mesas.isEmpty());
    }

    @Test
    void testFindMesasReservadasByRestaurante_comReservas() {

        // Arrange: Criação de mesas e reservas simuladas
        Mesa mesa1 = Mesa.builder().id("001-001").numeroMesa(1).build();
        Mesa mesa2 = Mesa.builder().id("001-002").numeroMesa(2).build();
        Mesa mesa3 = Mesa.builder().id("001-003").numeroMesa(3).build();
        Mesa mesa4 = Mesa.builder().id("001-004").numeroMesa(4).build();

        List<Mesa> listaMesa = List.of(mesa1,mesa2);
        List<Mesa> listaMesa2 = List.of(mesa3,mesa4);

        Reserva reserva1 = Reserva.builder()
                .mesas(listaMesa).build();

        Reserva reserva2 = Reserva.builder()
                .mesas(listaMesa2).build();

        LocalDateTime dataHoraReserva = LocalDateTime.of(2024, 2, 28, 19, 0);

        when(reservaHelper.getReservaByHoraMarcada(
                eq(1L),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(reserva1, reserva2));

        // Act: Chama o método
        Set<Mesa> mesasReservadas = mesaHelper.findMesasReservadasByRestaurante(1L, dataHoraReserva);

        // Assert: Verifica se as mesas reservadas estão corretas e sem duplicatas
        assertEquals(4, mesasReservadas.size());
        assertTrue(mesasReservadas.contains(mesa1));
        assertTrue(mesasReservadas.contains(mesa2));
        assertTrue(mesasReservadas.contains(mesa3));
        assertTrue(mesasReservadas.contains(mesa4));
    }

    @Test
    void testFindMesasReservadasByRestaurante_semReservas() {
        // Arrange: Simula que não há reservas no período
        LocalDateTime dataHoraReserva = LocalDateTime.of(2024, 2, 28, 19, 0);

        when(reservaHelper.getReservaByHoraMarcada(
                eq(1L),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());

        // Act: Chama o método
        Set<Mesa> mesasReservadas = mesaHelper.findMesasReservadasByRestaurante(1L, dataHoraReserva);

        // Assert: Verifica se o conjunto está vazio
        assertTrue(mesasReservadas.isEmpty());
    }

    @Test
    void testCalculaMesasDisponiveisByRestaurante_sucesso() {
        // Arrange
        Long restauranteId = 1L;
        int numeroDePessoas = 6;
        LocalDateTime dataHoraReserva = LocalDateTime.now();

        // Criando mesas disponíveis
        List<Mesa> mesasTotais = new ArrayList<>(List.of(
                Mesa.builder().id("001-001").numeroMesa(1).build(),
                Mesa.builder().id("001-002").numeroMesa(2).build(),
                Mesa.builder().id("001-003").numeroMesa(3).build()
        ));

        Set<Mesa> mesasReservadas = Set.of();

        when(mesaRepository.findByRestauranteId(restauranteId)).thenReturn(mesasTotais);
        when(reservaHelper.getReservaByHoraMarcada(anyLong(), any(), any())).thenReturn(List.of());

        // Act
        List<Mesa> mesasDisponiveis = mesaHelper.calculaMesasDisponiveisByRestaurante(restauranteId, numeroDePessoas, dataHoraReserva);

        // Assert
        assertEquals(3, mesasDisponiveis.size()); // Deve retornar 3 mesas
    }



    @Test
    void testCalculaMesasDisponiveisByRestaurante_mesasInsuficientes() {
        // Arrange
        Long restauranteId = 1L;
        int numeroDePessoas = 4;
        LocalDateTime dataHoraReserva = LocalDateTime.now();

        // Criando mesas disponíveis
        List<Mesa> mesasTotais = new ArrayList<>(List.of(
                Mesa.builder().id("001-001").numeroMesa(1).build(),
                Mesa.builder().id("001-002").numeroMesa(2).build()
        ));

        List<Mesa> mesasDaReserva = new ArrayList<>(List.of(
                Mesa.builder().id("001-001").numeroMesa(1).build()
        ));

        Reserva reserva = Reserva.builder().mesas(mesasDaReserva).build();


        when(mesaRepository.findByRestauranteId(restauranteId)).thenReturn(mesasTotais);
        when(reservaHelper.getReservaByHoraMarcada(anyLong(), any(), any()))
                .thenReturn(List.of(reserva));

        // Act & Assert
        assertThrows(MesaIndisponivelException.class, () ->
                mesaHelper.calculaMesasDisponiveisByRestaurante(restauranteId, numeroDePessoas, dataHoraReserva));
    }







}
