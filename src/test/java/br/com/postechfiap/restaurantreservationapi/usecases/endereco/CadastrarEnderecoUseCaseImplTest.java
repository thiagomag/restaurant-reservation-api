package br.com.postechfiap.restaurantreservationapi.usecases.endereco;

import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoAdapter;
import br.com.postechfiap.restaurantreservationapi.adapters.endereco.EnderecoResponseAdapter;
import br.com.postechfiap.restaurantreservationapi.interfaces.endereco.CadastrarEnderecoUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.endereco.EnderecoRepository;
import br.com.postechfiap.restaurantreservationapi.utils.EnderecoTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CadastrarEnderecoUseCaseImplTest {

    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private EnderecoAdapter enderecoAdapter;
    @Mock
    private EnderecoResponseAdapter enderecoResponseAdapter;

    private CadastrarEnderecoUseCase cadastrarEnderecoUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cadastrarEnderecoUseCase = new CadastrarEnderecoUseCaseImpl(enderecoRepository, enderecoAdapter,
                enderecoResponseAdapter);
    }

    @Test
    public void cadatrarEnderecoReturnSuccessfully() {
        //given
        final var enderecoRequest = EnderecoTestUtils.buildEnderecoRequest();
        final var endereco = EnderecoTestUtils.buildEndereco();
        final var enderecoResponse = EnderecoTestUtils.buildEnderecoResponse();

        when(enderecoAdapter.adapt(enderecoRequest))
                .thenReturn(endereco);
        when(enderecoRepository.save(endereco))
                .thenReturn(endereco);
        when(enderecoResponseAdapter.adapt(endereco))
                .thenReturn(enderecoResponse);

        //when
        final var result = cadastrarEnderecoUseCase.execute(enderecoRequest);

        //then
        assertNotNull(result);
        assertEquals(enderecoResponse, result);
        assertEquals(enderecoResponse.getId(), result.getId());
        assertEquals(enderecoResponse.getLogradouro(), result.getLogradouro());
        assertEquals(enderecoResponse.getNumero(), result.getNumero());
        assertEquals(enderecoResponse.getComplemento(), result.getComplemento());
        assertEquals(enderecoResponse.getBairro(), result.getBairro());
        assertEquals(enderecoResponse.getCidade(), result.getCidade());
        assertEquals(enderecoResponse.getEstado(), result.getEstado());
        assertEquals(enderecoResponse.getCep(), result.getCep());

        verify(enderecoAdapter, times(1)).adapt(enderecoRequest);
        verify(enderecoRepository, times(1)).save(endereco);
        verify(enderecoResponseAdapter, times(1)).adapt(endereco);
    }

}
