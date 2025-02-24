package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaTipoCozinhaRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
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
    public List<RestauranteResponse> execute(RestauranteBuscaTipoCozinhaRequest dto) {
        // Validação do tipo de cozinha, se necessário
        restauranteValidator.validateTipoCozinha(dto.getTipoCozinha());

        // Buscando restaurantes por tipo de cozinha
        List<Restaurante> restaurantes = restauranteRepository.findByTipoCozinha(dto.getTipoCozinha());

        return restaurantes.stream()
                .map(this::converterParaRestauranteResponse)
                .collect(Collectors.toList());
    }


    private RestauranteResponse converterParaRestauranteResponse(Restaurante restaurante) {
        return RestauranteResponse.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .endereco(EnderecoResponse.toDTO(restaurante.getEndereco()))
                .tipoCozinha(restaurante.getTipoCozinha())
                .horarioFuncionamento(restaurante.getHorarioFuncionamento())
                .capacidade(String.valueOf(restaurante.getCapacidade()))
                .build();
    }
}
