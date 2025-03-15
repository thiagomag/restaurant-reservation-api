package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaLocalizacaoRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.BuscarRestaurantesPorLocalizacaoUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.validator.RestauranteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuscarRestaurantesPorLocalizacaoUseCaseImpl implements BuscarRestaurantesPorLocalizacaoUseCase {

    private final RestauranteRepository restauranteRepository;
    private final RestauranteValidator restauranteValidator;

    @Override
    public List<RestauranteResponse> execute(RestauranteBuscaLocalizacaoRequest dto) {

        // 1.  Construa a consulta flexível, conforme os campos fornecidos no DTO
        List<Restaurante> restaurantes = restauranteRepository.findByLocalizacao(
                dto.getLogradouro(),
                dto.getCep(),
                dto.getCidade(),
                dto.getEstado()
        );

        // 2. Se nenhum restaurante for encontrado, lança uma exceção
        if (restaurantes.isEmpty()) {
            throw new RestauranteNotFoundException();
        }


        // 3. Converte a lista de restaurantes para uma lista de RestauranteResponse
        return restaurantes.stream()
                .map(RestauranteResponse::toDTO)
                .collect(Collectors.toList());
    }


}
