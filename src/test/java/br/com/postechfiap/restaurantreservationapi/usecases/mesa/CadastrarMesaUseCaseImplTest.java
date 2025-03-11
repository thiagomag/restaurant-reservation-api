package br.com.postechfiap.restaurantreservationapi.usecases.mesa;


import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponseList;
import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;

import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.MesaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
@Transactional
class CadastrarMesaUseCaseImplTest {

    @InjectMocks
    private CadastrarMesaUseCaseImpl cadastrarMesaUseCase;

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private MesaHelper mesaHelper;

    @Mock
    private RestauranteHelper restauranteHelper;

    @Mock
    private Restaurante restaurante;

    private MesaRequest request;

    @BeforeEach
    void setUp() {
        request = new MesaRequest(1L, 5);
    }

    @Test
    void deveCriarMesasComSucesso() {
        // Arrange
        when(restauranteHelper.getRestauranteById(request.getRestauranteId())).thenReturn(restaurante);
        when(mesaHelper.obterProximoNumeroMesa(request.getRestauranteId())).thenReturn(1, 2, 3, 4, 5); // Gerar números de mesa consecutivos
        when(mesaHelper.gerarIdentificadorMesa(request.getRestauranteId(), 1)).thenReturn("001-001");
        when(mesaHelper.gerarIdentificadorMesa(request.getRestauranteId(), 2)).thenReturn("001-002");
        when(mesaHelper.gerarIdentificadorMesa(request.getRestauranteId(), 3)).thenReturn("001-003");
        when(mesaHelper.gerarIdentificadorMesa(request.getRestauranteId(), 4)).thenReturn("001-004");
        when(mesaHelper.gerarIdentificadorMesa(request.getRestauranteId(), 5)).thenReturn("001-005");

        // Act
        MesaResponseList response = cadastrarMesaUseCase.execute(request);

        // Assert
        assertThat(response.getMesas()).hasSize(5);
        assertThat(response.getMesas().get(0).getNumeroMesa()).isEqualTo(1);
        assertThat(response.getMesas().get(1).getNumeroMesa()).isEqualTo(2);
        assertThat(response.getMesas().get(2).getNumeroMesa()).isEqualTo(3);
        assertThat(response.getMesas().get(3).getNumeroMesa()).isEqualTo(4);
        assertThat(response.getMesas().get(4).getNumeroMesa()).isEqualTo(5);

        verify(mesaRepository, times(5)).save(any(Mesa.class));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeMesasForInvalida() {
        // Arrange
        MesaRequest invalidRequest = new MesaRequest(1L, 0); // Quantidade de mesas <= 0

        // Act & Assert
        assertThatThrownBy(() -> cadastrarMesaUseCase.execute(invalidRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A quantidade de mesas deve ser maior que zero.");
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoForEncontrado() {
        // Arrange
        when(restauranteHelper.getRestauranteById(request.getRestauranteId())).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> cadastrarMesaUseCase.execute(request))
                .isInstanceOf(RestauranteNotFoundException.class);
    }

    @Test
    void deveLancarExcecaoQuandoErroAoSalvarMesa() {
        // Arrange
        when(restauranteHelper.getRestauranteById(request.getRestauranteId())).thenReturn(restaurante);
        when(mesaHelper.obterProximoNumeroMesa(request.getRestauranteId())).thenReturn(1, 2, 3, 4, 5);

        doThrow(new RuntimeException("Erro ao salvar a mesa")).when(mesaRepository).save(any(Mesa.class));

        // Act & Assert
        assertThatThrownBy(() -> cadastrarMesaUseCase.execute(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Erro ao salvar a mesa");
    }
}
