package br.com.postechfiap.restaurantreservationapi.usecases;

import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.MesaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CadastrarMesaUseCaseImpl {

    private final MesaRepository mesaRepository;
    private final MesaHelper mesaHelper;

    public MesaResponse cadastrarMesas(MesaRequest request) {
        List<Mesa> mesasCriadas = new ArrayList<>();

        for (int i = 0; i < request.getQuantidadeMesas(); i++) {
            Integer novoNumeroMesa = mesaHelper.obterProximoNumeroMesa(request.getRestauranteId());
            String identificadorMesa = mesaHelper.gerarIdentificadorMesa(request.getRestauranteId(), novoNumeroMesa);

            Mesa novaMesa = Mesa.builder()
                    .id(identificadorMesa)
                    .restauranteId(request.getRestauranteId())
                    .numeroMesa(novoNumeroMesa)
                    .capacidade(2)
                    .build();

            mesaRepository.save(novaMesa);
            mesasCriadas.add(novaMesa);
        }

        return new MesaResponse(mesasCriadas);
    }
}
