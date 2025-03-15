package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.validator.RestauranteValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BuscarRestaurantesPorNomeUseCaseImplTest {

    @InjectMocks
    private BuscarRestaurantesPorNomeUseCaseImpl buscarRestaurantesPorNomeUseCase;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RestauranteValidator restauranteValidator;

    @Mock
    private Restaurante restaurante;

    @Mock
    private Endereco endereco;

    static String nome = "Nome do Restaurante";

    @Test
    void deveRetornarRestaurantesQuandoEncontrados() {

        // Arrange
        RestauranteResponse restauranteResponse = RestauranteResponse.builder()
                .id(1L)
                .nome("Nome do Restaurante")
                .endereco(null)
                .tipoCozinha(TiposCozinhaEnum.ITALIANA)
                .horarioFuncionamento("10:00 - 22:00")
                .capacidade(100)
                .build();

        // Lista simulada com apenas 1 Restaurante
        List<Restaurante> restaurantes = List.of(restaurante);

        when(restauranteRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(restaurantes);
        doNothing().when(restauranteValidator).validateNome(nome);
        doNothing().when(restauranteValidator).validateNomeTamanho(nome);

        // Mockando a conversão para RestauranteResponse
        mockStatic(RestauranteResponse.class);
        when(RestauranteResponse.toDTO(any(Restaurante.class))).thenReturn(restauranteResponse);

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorNomeUseCase.execute(nome);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.getFirst().getNome()).isEqualTo("Nome do Restaurante");

        // Verificação de chamada do repositório
        verify(restauranteRepository).findByNomeContainingIgnoreCase(nome);

    }

    @Test
    void deveLancarExcecaoQuandoNenhumRestauranteForEncontrado() {
        // Arrange
        when(restauranteRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(Collections.emptyList());
        doNothing().when(restauranteValidator).validateNome(nome);
        doNothing().when(restauranteValidator).validateNomeTamanho(nome);

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorNomeUseCase.execute(nome))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado");
    }

    @Test
    void deveChamarValidadorDeNome() {

        // Arrange
        when(restauranteRepository.findByNomeContainingIgnoreCase(nome)).thenReturn(List.of(restaurante));
        lenient().when(restaurante.getEndereco()).thenReturn(endereco);
        lenient().when(endereco.getId()).thenReturn(1L);


        // Act
        buscarRestaurantesPorNomeUseCase.execute(nome);

        // Assert
        verify(restauranteValidator, times(1)).validateNome(nome);
        verify(restauranteValidator, times(1)).validateNomeTamanho(nome);
    }


    @Test
    void deveLancarExcecaoQuandoNomeForNull() {
        // Arrange
        final String nome = null;

        // Configurando o comportamento do mock
        doThrow(new IllegalArgumentException("O nome do restaurante não pode ser vazio ou nulo"))
                .when(restauranteValidator).validateNome(nome);


        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorNomeUseCase.execute(nome))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O nome do restaurante não pode ser vazio ou nulo");

        // Verifique se as validações foram chamadas
        verify(restauranteValidator, times(1)).validateNome(nome);

        // Verifique se o repositório NÃO foi chamado
        verify(restauranteValidator, times(0)).validateNomeTamanho(anyString());
        verify(restauranteRepository, times(0)).findByNomeContainingIgnoreCase(anyString());
    }


    @Test
    void deveLancarExcecaoQuandoNomeForMenorQueTresCaracteres() {
        // Arrange
        nome ="A";

        // Configurando o comportamento do mock
        doNothing().when(restauranteValidator).validateNome(nome);  // Primeiro mock: não faz nada
        doThrow(new IllegalArgumentException("O nome do restaurante deve ter pelo menos 3 caracteres"))
                .when(restauranteValidator).validateNomeTamanho(nome);  // Segundo mock: lança a exceção desejada

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorNomeUseCase.execute(nome))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O nome do restaurante deve ter pelo menos 3 caracteres");

        // Verifique se as validações foram chamadas
        verify(restauranteValidator, times(1)).validateNome(nome);
        verify(restauranteValidator, times(1)).validateNomeTamanho(nome);

        // Verifique se o repositório NÃO foi chamado
        verify(restauranteRepository, times(0)).findByNomeContainingIgnoreCase(nome);
    }




}
