package br.com.postechfiap.restaurantreservationapi.usecases.mesa;

import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaRequest;
import br.com.postechfiap.restaurantreservationapi.dto.mesa.MesaResponseList;
import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
import br.com.postechfiap.restaurantreservationapi.exception.restaurante.RestauranteNotFoundException;
import br.com.postechfiap.restaurantreservationapi.interfaces.mesa.MesaRepository;
import br.com.postechfiap.restaurantreservationapi.utils.MesaHelper;
import br.com.postechfiap.restaurantreservationapi.utils.RestauranteHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Define o perfil de teste, caso tenha configurações específicas no application-test.properties
public class CadastrarMesaUseCaseImplIT {

    @Autowired
    private CadastrarMesaUseCaseImpl cadastrarMesaUseCase; // O UseCase que vamos testar

    @Autowired
    private MesaRepository mesaRepository; // Repositório para verificar a persistência
    @Autowired
    private RestauranteHelper restauranteHelper; // Helper para ajudar a manipular dados do restaurante
    @Autowired
    private MesaHelper mesaHelper; // Helper para manipular dados de mesa

    @Test
    public void deveCadastrarMesasComSucesso() {
        // ARRANGE
        // Criando um request válido
        MesaRequest request = new MesaRequest(1L, 3);

        // ACT - Executando o caso de uso
        MesaResponseList response = cadastrarMesaUseCase.execute(request);

        // ASSERT - Verificando se as mesas foram criadas
        System.out.println("Tamanho da resposta (mesas): " + response.getMesas().size()); // Verificando a quantidade de mesas na resposta
        assertNotNull(response);
        assertEquals(3, response.getMesas().size()); // Espera-se que 3 mesas tenham sido criadas

        // Verificando se as mesas estão salvas no banco
        List<Mesa> mesasSalvas = mesaRepository.findByRestauranteId(1L);
        System.out.println("Tamanho das mesas salvas no banco: " + mesasSalvas.size()); // Verificando a quantidade de mesas no banco
        assertEquals(5, mesasSalvas.size()); // Restaurante 1 deve ter 5 mesas salvas, já existiam 2


        for (Mesa mesa : mesasSalvas) {
            assertEquals(1L, mesa.getRestaurante().getId());
            assertTrue(mesa.getNumeroMesa() > 0);
        }
    }

    @Test
    public void deveLancarExcecaoQuandoQuantidadeMesasForInvalida() {
        // ARRANGE - Criando um request inválido (quantidadeMesas <= 0)
        // Criando um request válido
        MesaRequest request = new MesaRequest(1L, 0);

        // ACT & ASSERT - Verificando se a exceção é lançada
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cadastrarMesaUseCase.execute(request);
        });

        // Verificando a mensagem da exceção
        assertEquals("A quantidade de mesas deve ser maior que zero.", exception.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoRestauranteNaoExistir() {
        // ARRANGE - Criando um request com restaurante inexistente
        MesaRequest request = new MesaRequest();
        request.setRestauranteId(999L); // ID de restaurante inexistente
        request.setQuantidadeMesas(3); // Quantidade válida

        // ACT & ASSERT - Verificando se a exceção é lançada
        Exception exception = assertThrows(RestauranteNotFoundException.class, () -> {
            cadastrarMesaUseCase.execute(request);
        });

        // Verificando a mensagem da exceção
        assertEquals("Restaurante não encontrado.", exception.getMessage());
    }
}