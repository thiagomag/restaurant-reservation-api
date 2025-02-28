package br.com.postechfiap.restaurantreservationapi.usecases.restaurante;

import br.com.postechfiap.restaurantreservationapi.dto.restaurante.RestauranteResponse;
import br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca.RestauranteBuscaNomeRequest;
import br.com.postechfiap.restaurantreservationapi.entities.Endereco;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import br.com.postechfiap.restaurantreservationapi.validator.RestauranteValidator;
import org.junit.jupiter.api.BeforeEach;
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

    private RestauranteBuscaNomeRequest request;

    @BeforeEach
    void setUp() {
        request = new RestauranteBuscaNomeRequest("Nome do Restaurante");
    }

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

        when(restauranteRepository.findByNomeContainingIgnoreCase(request.getNome())).thenReturn(restaurantes);
        doNothing().when(restauranteValidator).validateNome(request.getNome());
        doNothing().when(restauranteValidator).validateNomeTamanho(request.getNome());

        // Mockando a conversão para RestauranteResponse
        mockStatic(RestauranteResponse.class);
        when(RestauranteResponse.toDTO(any(Restaurante.class))).thenReturn(restauranteResponse);

        // Act
        List<RestauranteResponse> response = buscarRestaurantesPorNomeUseCase.execute(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0).getNome()).isEqualTo("Nome do Restaurante");

        // Verificação de chamada do repositório
        verify(restauranteRepository).findByNomeContainingIgnoreCase(request.getNome());

    }

    @Test
    void deveLancarExcecaoQuandoNenhumRestauranteForEncontrado() {
        // Arrange
        when(restauranteRepository.findByNomeContainingIgnoreCase(request.getNome())).thenReturn(Collections.emptyList());
        doNothing().when(restauranteValidator).validateNome(request.getNome());
        doNothing().when(restauranteValidator).validateNomeTamanho(request.getNome());

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorNomeUseCase.execute(request))
                .isInstanceOf(RestauranteNotFoundException.class)
                .hasMessageContaining("Restaurante não encontrado");
    }

    @Test
    void deveChamarValidadorDeNome() {

        // Arrange
        when(restauranteRepository.findByNomeContainingIgnoreCase(request.getNome())).thenReturn(List.of(restaurante));
        lenient().when(restaurante.getEndereco()).thenReturn(endereco);
        lenient().when(endereco.getId()).thenReturn(1L);


        // Act
        buscarRestaurantesPorNomeUseCase.execute(request);

        // Assert
        verify(restauranteValidator, times(1)).validateNome(request.getNome());
        verify(restauranteValidator, times(1)).validateNomeTamanho(request.getNome());
    }


    @Test
    void deveLancarExcecaoQuandoNomeForNull() {
        // Arrange
        RestauranteBuscaNomeRequest requestComNomeNull = new RestauranteBuscaNomeRequest(null);  // Nome com 1 caractere

        // Configurando o comportamento do mock
        doThrow(new IllegalArgumentException("O nome do restaurante não pode ser vazio ou nulo"))
                .when(restauranteValidator).validateNome(requestComNomeNull.getNome());


        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorNomeUseCase.execute(requestComNomeNull))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O nome do restaurante não pode ser vazio ou nulo");

        // Verifique se as validações foram chamadas
        verify(restauranteValidator, times(1)).validateNome(requestComNomeNull.getNome());

        // Verifique se o repositório NÃO foi chamado
        verify(restauranteValidator, times(0)).validateNomeTamanho(requestComNomeNull.getNome());
        verify(restauranteRepository, times(0)).findByNomeContainingIgnoreCase(requestComNomeNull.getNome());
    }


    @Test
    void deveLancarExcecaoQuandoNomeForMenorQueTresCaracteres() {
        // Arrange
        RestauranteBuscaNomeRequest requestComNomeCurto = new RestauranteBuscaNomeRequest("A");

        // Configurando o comportamento do mock
        doNothing().when(restauranteValidator).validateNome(requestComNomeCurto.getNome());  // Primeiro mock: não faz nada
        doThrow(new IllegalArgumentException("O nome do restaurante deve ter pelo menos 3 caracteres"))
                .when(restauranteValidator).validateNomeTamanho(requestComNomeCurto.getNome());  // Segundo mock: lança a exceção desejada

        // Act & Assert
        assertThatThrownBy(() -> buscarRestaurantesPorNomeUseCase.execute(requestComNomeCurto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O nome do restaurante deve ter pelo menos 3 caracteres");

        // Verifique se as validações foram chamadas
        verify(restauranteValidator, times(1)).validateNome(requestComNomeCurto.getNome());
        verify(restauranteValidator, times(1)).validateNomeTamanho(requestComNomeCurto.getNome());

        // Verifique se o repositório NÃO foi chamado
        verify(restauranteRepository, times(0)).findByNomeContainingIgnoreCase(requestComNomeCurto.getNome());
    }




}
