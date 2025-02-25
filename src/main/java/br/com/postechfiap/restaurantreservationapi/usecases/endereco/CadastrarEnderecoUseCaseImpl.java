package br.com.postechfiap.restaurantreservationapi.usecases.endereco;

import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.endereco.CadastrarEnderecoUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.endereco.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarEnderecoUseCaseImpl implements CadastrarEnderecoUseCase {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoAdapter enderecoAdapter;
    private final EnderecoResponseAdapter enderecoResponseAdapter;

    @Override
    public EnderecoResponse execute(EnderecoRequest enderecoRequest) {
        final var endereco = enderecoAdapter.adapt(enderecoRequest);
        return enderecoResponseAdapter.adapt(enderecoRepository.save(endereco));
    }
}
