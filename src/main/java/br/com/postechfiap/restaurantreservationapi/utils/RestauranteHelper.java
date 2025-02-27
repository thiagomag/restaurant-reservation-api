package br.com.postechfiap.restaurantreservationapi.utils;

import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RestauranteHelper {

    private final RestauranteRepository restauranteRepository;


    public Restaurante getRestauranteById(Long restauranteId){
        return restauranteRepository.findById(restauranteId).orElseThrow(RestauranteNotFoundException::new);
    }

    public Restaurante validateRestauranteExists(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(RestauranteNotFoundException::new);
    }




}
