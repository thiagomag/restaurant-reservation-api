package br.com.postechfiap.restaurantreservationapi.utils;

import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.exception.mesa.MesaIndisponivelException;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.MesaRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MesaHelper {

    private final MesaRepository mesaRepository;
    private final ReservaRepository reservaRepository;

    public Integer obterProximoNumeroMesa(Long restauranteId) {
        return mesaRepository.findTopByRestauranteIdOrderByNumeroMesaDesc(restauranteId)
                .map(mesa -> mesa.getNumeroMesa() + 1)
                .orElse(1);
    }


    //Gera um identificador único no formato "001-001" (Restaurante-Mesa)
    public String gerarIdentificadorMesa(Long restauranteId, Integer numeroMesa) {
        return String.format("%03d-%03d", restauranteId, numeroMesa);
    }


    public List<Mesa> findMesasDisponiveis(Long restauranteId, int numeroDePessoas, LocalDateTime dataHoraReserva) {

        // Passo 1: Buscar todas as mesas no restaurante
        List<Mesa> mesasTotais = mesaRepository.findByRestauranteId(restauranteId);
        mesasTotais.sort(Comparator.comparingInt(mesa -> Integer.parseInt(mesa.getId().split("-")[1])));

        // Passo 2: Definir o intervalo de tempo para 1 hora antes e 2 horas depois
        LocalDateTime inicioIntervalo = dataHoraReserva.minusHours(1);
        LocalDateTime fimIntervalo = dataHoraReserva.plusHours(2);

        // Passo 3: Buscar reservas para o restaurante dentro do intervalo de tempo
        List<Reserva> reservasExistentes = reservaRepository.findByRestauranteIdAndDataHoraReservaBetween(
                restauranteId, inicioIntervalo, fimIntervalo);

        // Passo 4: Filtrar as mesas que estão reservadas
        Set<Mesa> mesasIndisponiveis = reservasExistentes.stream()
                .map(Reserva::getMesa)  // Agora, uma reserva tem uma única mesa
                .collect(Collectors.toSet());

        // Passo 5: Subtrair mesas reservadas das mesas totais para encontrar as mesas disponíveis
        List<Mesa> mesasDisponiveis = mesasTotais.stream()
                .filter(mesa -> !mesasIndisponiveis.contains(mesa))
                .collect(Collectors.toList());

        // Passo 6: Calcular o número mínimo de mesas necessárias
        int mesasNecessarias = (numeroDePessoas + 1) / 2; // Arredonda para cima se for ímpar

        // Passo 7: Verificar se há mesas suficientes
        if (mesasDisponiveis.size() < mesasNecessarias) {
            throw new MesaIndisponivelException("Não há mesas suficientes para acomodar " + numeroDePessoas + " pessoas.");
        }

        // Passo 8: Retornar a quantidade exata de mesas necessárias
        return mesasDisponiveis.subList(0, mesasNecessarias);
    }
}