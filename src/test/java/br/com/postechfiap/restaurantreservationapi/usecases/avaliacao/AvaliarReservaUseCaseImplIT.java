package br.com.postechfiap.restaurantreservationapi.usecases.avaliacao;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.entities.*;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaJaAvaliadaException;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import br.com.postechfiap.restaurantreservationapi.validator.AvaliacaoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
@ActiveProfiles("test")
public class AvaliarReservaUseCaseImplIT {

    @Autowired
    private  AvaliarReservaUseCaseImpl avaliarReservaUseCase;

    @Autowired
    private  AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private  ReservaHelper reservaHelper;

    @Autowired
    private ReservaRepository reservaRepository;

    @Mock
    private AvaliacaoValidator avaliacaoValidator;

    @BeforeEach
    public void setup() {
        // Inicializa o mock do AvaliacaoValidator
        MockitoAnnotations.openMocks(this);

        // Injetando o mock na classe AvaliarReservaUseCaseImpl
        avaliarReservaUseCase = new AvaliarReservaUseCaseImpl(avaliacaoRepository, avaliacaoValidator, reservaHelper);

    }

    @Test
    public void deveCriarUmaAvaliacaoComSucesso() {

        // Arrange
        AvaliacaoRequest request = new AvaliacaoRequest(1L, 5, "Ótima experiência!");

        // Act
        AvaliacaoResponse response = avaliarReservaUseCase.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals(5, response.getNota());
        assertEquals("Ótima experiência!", response.getComentario());

        // Validar persistência no banco
        Optional<Avaliacao> avaliacaoSalva = avaliacaoRepository.findById(response.getId());
        assertTrue(avaliacaoSalva.isPresent());
    }

    @Test
    public void deveCriarUmaAvaliacaoComSucessoEImpedirSegundaAvaliacaoParaReserva() {

        // ARRANGE

        // Criar um request válido para a primeira avaliação
        AvaliacaoRequest requestPrimeiraAvaliacao = new AvaliacaoRequest(1L, 5, "Ótima experiência!");

        // ACT: Executar a primeira avaliação
        AvaliacaoResponse responsePrimeiraAvaliacao = avaliarReservaUseCase.execute(requestPrimeiraAvaliacao);

        // ASSERT: Validar a resposta da primeira avaliação
        assertNotNull(responsePrimeiraAvaliacao);
        assertEquals(5, responsePrimeiraAvaliacao.getNota());
        assertEquals("Ótima experiência!", responsePrimeiraAvaliacao.getComentario());

        // Validar persistência no banco após a primeira avaliação
        Optional<Avaliacao> avaliacaoSalva = avaliacaoRepository.findById(responsePrimeiraAvaliacao.getId());
        assertTrue(avaliacaoSalva.isPresent());

        // ARRANGE: Criar um request para a segunda tentativa de avaliação para a mesma reserva
        AvaliacaoRequest requestSegundaAvaliacao = new AvaliacaoRequest(1L, 4, "Boa experiência!");

        // Configurar o mock para lançar a exceção na segunda tentativa de avaliação usando doThrow
        doThrow(new ReservaJaAvaliadaException(1L)).when(avaliacaoValidator).validarSeReservaJaFoiAvaliada(anyLong());

        // ACT: Validar que ao tentar executar o caso de uso para a segunda avaliação, uma exceção seja lançada
        Exception exception = assertThrows(ReservaJaAvaliadaException.class, () -> {
            // Aqui, tentamos executar a segunda avaliação, que deve lançar a exceção configurada
            avaliarReservaUseCase.execute(requestSegundaAvaliacao);
        });

        // ASSERT: Validar que a exceção foi lançada
        assertEquals("Reserva 1 já foi avaliada.", exception.getMessage());
    }


    @Test
    public void deveRetornarErroQuandoNotaForInvalida() {

        // Arrange
        AvaliacaoRequest request = new AvaliacaoRequest(1L, 10, "Comentário inválido!");

        // Mockando o comportamento do AvaliacaoValidator para lançar a exceção
        doThrow(new IllegalArgumentException("A nota deve ser entre 1 e 5."))
                .when(avaliacaoValidator).validarNota(request.getNota());

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            avaliarReservaUseCase.execute(request);
        });

        // Assert
        assertEquals("A nota deve ser entre 1 e 5.", exception.getMessage());
    }


}