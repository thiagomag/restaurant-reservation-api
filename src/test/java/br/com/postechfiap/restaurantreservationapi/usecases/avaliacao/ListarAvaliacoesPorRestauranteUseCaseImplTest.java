package br.com.postechfiap.restaurantreservationapi.usecases.avaliacao;

import br.com.postechfiap.restaurantreservationapi.entities.Avaliacao;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.entities.Restaurante;
import br.com.postechfiap.restaurantreservationapi.entities.Usuario;
import br.com.postechfiap.restaurantreservationapi.enuns.NotaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.ListarAvaliacoesPorRestauranteUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.restaurante.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class ListarAvaliacoesPorRestauranteUseCaseImplTest {

    @Mock
    private AvaliacaoRepository avaliacaoRepository;
    @Mock
    private RestauranteRepository restauranteRepository;

    private ListarAvaliacoesPorRestauranteUseCase listarAvaliacoesPorRestauranteUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        listarAvaliacoesPorRestauranteUseCase = new ListarAvaliacoesPorRestauranteUseCaseImpl(avaliacaoRepository, restauranteRepository);
    }

    @Test
    public void buscarAvaliacoesPorRestauranteReturnSuccessfully() {
        //given
        final var restauranteId = 1L;
        final var avaliacao = new Avaliacao();
        avaliacao.setId(1L);
        avaliacao.setReserva(Reserva.builder().id(1L)
                .usuario(Usuario.builder().id(1L).build())
                .restaurante(Restaurante.builder().id(1L).build())
                .build());
        avaliacao.setNota(NotaEnum.EXCELENTE);
        avaliacao.setComentario("Comentário");



        when(restauranteRepository.findById(anyLong()))
                .thenReturn(Optional.of(Restaurante.builder().id(1L).build()));
        when(avaliacaoRepository.findByReserva_Restaurante_IdAndDeletedTmspIsNull(anyLong()))
                .thenReturn(List.of(avaliacao));

        //when
        final var actual = listarAvaliacoesPorRestauranteUseCase.execute(restauranteId);

        //then
        assertNotNull(actual);
        assertNotNull(actual.get(0));
    }

    @Test
    public void buscarAvaliacoesPorRestauranteReturnEmpty() {
        //given
        final var restauranteId = 1L;

        when(restauranteRepository.findById(anyLong()))
                .thenReturn(Optional.of(Restaurante.builder().id(1L).build()));
        when(avaliacaoRepository.findByReserva_Restaurante_IdAndDeletedTmspIsNull(anyLong()))
                .thenReturn(List.of());

        //when
        final var actual = listarAvaliacoesPorRestauranteUseCase.execute(restauranteId);

        //then
        assertNotNull(actual);
    }

    @Test
    public void buscarAvaliacoesPorRestauranteReturnError() {
        //given
        final var restauranteId = 1L;

        when(restauranteRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() ->listarAvaliacoesPorRestauranteUseCase.execute(restauranteId))
                .isInstanceOf(RestauranteNotFoundException.class);

        verifyNoInteractions(avaliacaoRepository);
    }
}
