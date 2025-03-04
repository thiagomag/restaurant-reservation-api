package br.com.postechfiap.restaurantreservationapi.usecases.avaliacao;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.entities.*;
import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import br.com.postechfiap.restaurantreservationapi.interfaces.reserva.ReservaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.ReservaHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AvaliarReservaUseCaseImplIT {

    @Autowired
    private  AvaliarReservaUseCaseImpl avaliarReservaUseCase;

    @Autowired
    private  AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private  ReservaHelper reservaHelper;

    @Autowired
    private ReservaRepository reservaRepository;

    @BeforeEach
    public void setup() {

        Usuario usuario = Usuario.builder().id(1L).nome("Catarina").email("Catarinalinda@.com").telefone("1234").build();
        Restaurante restaurante = Restaurante.builder().id(1L).nome("Restaurante Catarina")
                .endereco(null).tipoCozinha(TiposCozinhaEnum.AMAZONICA).horarioFuncionamento("sddwdw").capacidade(10).build();
        Mesa mesa = Mesa.builder().id("001-001").numeroMesa(1).build();
        var mesas =  List.of(mesa);
        var localData = LocalDateTime.of(2024,2,3,10,10,0);

        Reserva reserva = Reserva.builder().id(1L).usuario(usuario).mesas(mesas)
                .dataHoraReserva(localData).numeroDePessoas(2).restaurante(restaurante).build();

        reservaRepository.save(reserva);
        reservaRepository.flush();
    }


    @Test
    public void deveCriarUmaAvaliacaoComSucesso() {

        // Listar todas as reservas antes do teste
        System.out.println("Reservas no banco antes do teste:");
        reservaRepository.findAll().forEach(System.out::println);

        // Simular uma reserva existente
        Reserva reserva = reservaHelper.getReservaById(1L);

        // Criar um request válido
        AvaliacaoRequest request = new AvaliacaoRequest(1L, 5, "Ótima experiência!");

        // Executar o caso de uso
        AvaliacaoResponse response = avaliarReservaUseCase.execute(request);

        // Validar resposta
        assertNotNull(response);
        assertEquals(5, response.getNota());
        assertEquals("Ótima experiência!", response.getComentario());

        // Validar persistência no banco
        Optional<Avaliacao> avaliacaoSalva = avaliacaoRepository.findById(response.getId());
        assertTrue(avaliacaoSalva.isPresent());
    }
}