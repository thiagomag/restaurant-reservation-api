package br.com.postechfiap.restaurantreservationapi.interfaces.endereco;

import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.endereco.EnderecoResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.UseCase;

public interface CadastrarEnderecoUseCase extends UseCase<EnderecoRequest, EnderecoResponse> {
}
