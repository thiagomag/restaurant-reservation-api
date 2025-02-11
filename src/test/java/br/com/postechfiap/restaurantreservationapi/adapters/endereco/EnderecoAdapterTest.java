package br.com.postechfiap.restaurantreservationapi.adapters.endereco;

import br.com.postechfiap.restaurantreservationapi.utils.EnderecoTestUtils;
import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnderecoAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var enderecoRequest = EnderecoTestUtils.buildEnderecoRequest();

        //when
        final var actual = new EnderecoAdapter(jsonUtils).adapt(enderecoRequest);

        //then
        assertEquals(enderecoRequest.getCep(), actual.getCep());
        assertEquals(enderecoRequest.getLogradouro(), actual.getLogradouro());
        assertEquals(enderecoRequest.getNumero(), actual.getNumero());
        assertEquals(enderecoRequest.getComplemento(), actual.getComplemento());
        assertEquals(enderecoRequest.getBairro(), actual.getBairro());
        assertEquals(enderecoRequest.getCidade(), actual.getCidade());
        assertEquals(enderecoRequest.getEstado(), actual.getEstado());
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach2() {
        //given
        final var enderecoRequest = EnderecoTestUtils.buildEnderecoRequest();
        enderecoRequest.setLogradouro("Novo Logradouro");
        final var endereco = EnderecoTestUtils.buildEndereco();

        //when
        final var actual = new EnderecoAdapter(jsonUtils).adapt(enderecoRequest, endereco);

        //then
        assertEquals(enderecoRequest.getCep(), actual.getCep());
        assertEquals(enderecoRequest.getLogradouro(), actual.getLogradouro());
        assertEquals(enderecoRequest.getNumero(), actual.getNumero());
        assertEquals(enderecoRequest.getComplemento(), actual.getComplemento());
        assertEquals(enderecoRequest.getBairro(), actual.getBairro());
        assertEquals(enderecoRequest.getCidade(), actual.getCidade());
        assertEquals(enderecoRequest.getEstado(), actual.getEstado());
    }
}
