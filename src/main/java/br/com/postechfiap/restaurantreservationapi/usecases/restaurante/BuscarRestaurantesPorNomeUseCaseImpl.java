package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorNomeUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.validator.RestauranteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BuscarRestaurantesPorNomeUseCaseImpl implements BuscarRestaurantesPorNomeUseCase {

    private final RestauranteRepository restauranteRepository;
    private final RestauranteValidator restauranteValidator;

    @Override
    public List<RestauranteResponse> execute(String nome) {

        // 1. Validação do nome
        restauranteValidator.validateNome(nome);
        restauranteValidator.validateNomeTamanho(nome);

        // 2. Busca no repositório por nome (contém o nome, ignorando maiúsculas/minúsculas)
        List<Restaurante> restaurantes = restauranteRepository.findByNomeContainingIgnoreCase(nome);

        // 3. Se nenhum restaurante for encontrado, lança uma exceção
        if (restaurantes.isEmpty()) {
            throw new RestauranteNotFoundException();
        }

        // 4. Retorna a resposta com os restaurantes encontrados
        return restaurantes.stream()
                .map(RestauranteResponse::toDTO)
                .collect(Collectors.toList());
    }
}