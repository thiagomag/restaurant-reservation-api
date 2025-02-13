package br.com.postechfiap.restaurantreservationapi.utils;

import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.exception.mesa.MesaIndisponivelException;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MesaHelper {

    private final MesaRepository mesaRepository;

    public Integer obterProximoNumeroMesa(Long restauranteId) {
        return mesaRepository.findTopByRestauranteIdOrderByNumeroMesaDesc(restauranteId)
                .map(mesa -> mesa.getNumeroMesa() + 1)
                .orElse(1);
    }


    //Gera um identificador único no formato "001-001" (Restaurante-Mesa)
    public String gerarIdentificadorMesa(Long restauranteId, Integer numeroMesa) {
        return String.format("%03d-%03d", restauranteId, numeroMesa);
    }


    public List<Mesa> findMesasDisponiveis(Long restauranteId, int numeroDePessoas) {

        // Buscar todas as mesas disponíveis no restaurante e ordenar pelo ID
        List<Mesa> mesasDisponiveis = mesaRepository.findByRestauranteId(restauranteId);
        mesasDisponiveis.sort(Comparator.comparingInt
                (mesa -> Integer.parseInt(mesa.getId().split("-")[1])));

        // Calcular o número mínimo de mesas necessárias
        int mesasNecessarias = (numeroDePessoas + 1) / 2; // Arredonda para cima se for ímpar

        // Verificar se há mesas suficientes
        if (mesasDisponiveis.size() < mesasNecessarias) {
            throw new MesaIndisponivelException("Não há mesas suficientes para acomodar " + numeroDePessoas + " pessoas.");
        }

        // Retornar a quantidade exata de mesas necessárias
        return mesasDisponiveis.subList(0, mesasNecessarias);
    }
}