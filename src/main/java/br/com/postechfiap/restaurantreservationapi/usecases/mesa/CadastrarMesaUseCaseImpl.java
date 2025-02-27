package br.com.postechfiap.restaurantreservationapi.usecases.mesa;

import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponse;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponseList;
import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.CadastrarMesaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.MesaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CadastrarMesaUseCaseImpl implements CadastrarMesaUseCase {

    private final MesaRepository mesaRepository;
    private final MesaHelper mesaHelper;
    private final RestauranteHelper restauranteHelper;

    @Override
    @Transactional
    public MesaResponseList execute(MesaRequest request) {
        // Verificação de entrada
        if (request.getQuantidadeMesas() <= 0) {
            throw new IllegalArgumentException("A quantidade de mesas deve ser maior que zero.");
        }

        Restaurante restaurante = restauranteHelper.getRestauranteById(request.getRestauranteId());
        if (restaurante == null) {
            throw new RestauranteNotFoundException();
        }

        List<MesaResponse> mesasCriadas = new ArrayList<>();

        for (int i = 0; i < request.getQuantidadeMesas(); i++) {
            // Obtenção do próximo número da mesa
            Integer novoNumeroMesa = mesaHelper.obterProximoNumeroMesa(request.getRestauranteId());
            String identificadorMesa = mesaHelper.gerarIdentificadorMesa(request.getRestauranteId(), novoNumeroMesa);

            // Criação da nova mesa
            Mesa novaMesa = Mesa.builder()
                    .id(identificadorMesa)
                    .restaurante(restaurante)
                    .numeroMesa(novoNumeroMesa)
                    .build();

            try {
                // Salvando a mesa
                mesaRepository.save(novaMesa);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao salvar a mesa: " + e.getMessage());
            }

            // Convertendo a mesa para o formato de resposta e adicionando à lista
            mesasCriadas.add(
                    MesaResponse.builder()
                            .id(novaMesa.getId())
                            .numeroMesa(novaMesa.getNumeroMesa())
                            .restauranteId(request.getRestauranteId())
                            .build()
            );
        }

        // Retorno da lista de mesas criadas
        return new MesaResponseList(mesasCriadas);
    }
}
