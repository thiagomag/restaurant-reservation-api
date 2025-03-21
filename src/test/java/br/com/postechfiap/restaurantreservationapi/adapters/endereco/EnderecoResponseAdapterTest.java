package br.com.postechfiap.restaurantreservationapi.adapters.endereco;

import br.com.postechfiap.restaurantreservationapi.utils.endereco.EnderecoTestUtils;
import br.com.postechfiap.restaurantreservationapi.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnderecoResponseAdapterTest {

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach1() {
        //given
        final var endereco = EnderecoTestUtils.buildEndereco();

        //when
        final var actual = new EnderecoResponseAdapter(jsonUtils).adapt(endereco);

        //then
        assertEquals(endereco.getId(), actual.getId());
        assertEquals(endereco.getCep(), actual.getCep());
        assertEquals(endereco.getLogradouro(), actual.getLogradouro());
        assertEquals(endereco.getNumero(), actual.getNumero());
        assertEquals(endereco.getBairro(), actual.getBairro());
        assertEquals(endereco.getCidade(), actual.getCidade());
        assertEquals(endereco.getEstado(), actual.getEstado());
    }

    @Test
    public void adaptShouldReturnSuccessfullyApproach2() {
        //given
        final var endereco = EnderecoTestUtils.buildEndereco();
        final var enderecoResponse = EnderecoTestUtils.buildEnderecoResponse();

        //when
        final var actual = new EnderecoResponseAdapter(jsonUtils).adapt(endereco, enderecoResponse);

        //then
        assertEquals(endereco.getId(), actual.getId());
        assertEquals(endereco.getCep(), actual.getCep());
        assertEquals(endereco.getLogradouro(), actual.getLogradouro());
        assertEquals(endereco.getNumero(), actual.getNumero());
        assertEquals(endereco.getBairro(), actual.getBairro());
        assertEquals(endereco.getCidade(), actual.getCidade());
        assertEquals(endereco.getEstado(), actual.getEstado());
    }
}
