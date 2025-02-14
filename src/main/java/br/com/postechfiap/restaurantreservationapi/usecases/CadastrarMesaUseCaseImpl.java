package br.com.postechfiap.restaurantreservationapi.usecases;

import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.CadastrarMesaUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.MesaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CadastrarMesaUseCaseImpl implements CadastrarMesaUseCase {

    private final MesaRepository mesaRepository;
    private final MesaHelper mesaHelper;
    private final RestauranteHelper restauranteHelper;

    @Override
    public MesaResponse execute (MesaRequest request) {
        List<Mesa> mesasCriadas = new ArrayList<>();

        for (int i = 0; i < request.getQuantidadeMesas(); i++) {
            Integer novoNumeroMesa = mesaHelper.obterProximoNumeroMesa(request.getRestauranteId());
            String identificadorMesa = mesaHelper.gerarIdentificadorMesa(request.getRestauranteId(), novoNumeroMesa);

            Mesa novaMesa = Mesa.builder()
                    .id(identificadorMesa)
                    .restaurante(restauranteHelper.getRestauranteById(request.getRestauranteId()))
                    .numeroMesa(novoNumeroMesa)
                    .build();

            mesaRepository.save(novaMesa);
            mesasCriadas.add(novaMesa);
        }

        return new MesaResponse(mesasCriadas);
    }
}
