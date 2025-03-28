package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorTipoDeCozinhaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.validator.RestauranteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuscarRestaurantesPorTipoDeCozinhaUseCaseImpl implements BuscarRestaurantesPorTipoDeCozinhaUseCase {

    private final RestauranteRepository restauranteRepository; // Repositório de Restaurantes
    private final RestauranteValidator restauranteValidator; // Validador de Restaurantes (se necessário)

    @Override
    public List<RestauranteResponse> execute(String tipoCozinha) {
        final var tipoCozinhaEnum = TiposCozinhaEnum.findBy(tipoCozinha);

        // 1. Validação do tipo de cozinha
        restauranteValidator.validateTipoCozinha(tipoCozinhaEnum);

        // 2. Buscando restaurantes por tipo de cozinha
        List<Restaurante> restaurantes = restauranteRepository.findByTipoCozinha(tipoCozinhaEnum);

        // 3. Se nenhum restaurante for encontrado, lança uma exceção
        if (restaurantes.isEmpty()){
            throw new RestauranteNotFoundException();
        }

        return restaurantes.stream()
                .map(RestauranteResponse::toDTO)
                .collect(Collectors.toList());
    }


}
