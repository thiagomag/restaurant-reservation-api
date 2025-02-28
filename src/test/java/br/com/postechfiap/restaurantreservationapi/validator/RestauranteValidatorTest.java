package br.com.postechfiap.restaurantreservationapi.validator;

import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
class RestauranteValidatorTest {

    private RestauranteValidator restauranteValidator;

    @BeforeEach
    void setUp() {
        restauranteValidator = new RestauranteValidator();
    }

    @Test
    void deveLancarExcecao_SeNomeForNuloOuVazio() {
        // Testando nome nulo
        assertThatThrownBy(() -> restauranteValidator.validateNome(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O nome do restaurante não pode ser vazio ou nulo");

        // Testando nome vazio
        assertThatThrownBy(() -> restauranteValidator.validateNome(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O nome do restaurante não pode ser vazio ou nulo");

        // Testando nome com apenas espaços
        assertThatThrownBy(() -> restauranteValidator.validateNome("  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O nome do restaurante não pode ser vazio ou nulo");
    }



    @Test
    void deveLancarExcecao_SeNomeForMenorQueTresCaracteres() {
        // Testando nome com menos de 3 caracteres
        assertThatThrownBy(() -> restauranteValidator.validateNomeTamanho("A"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O nome do restaurante deve ter pelo menos 3 caracteres");

        assertThatThrownBy(() -> restauranteValidator.validateNomeTamanho("AB"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O nome do restaurante deve ter pelo menos 3 caracteres");
    }

    @Test
    void naoDeveLancarExcecao_SeNomeForValido() {
        // Testando nome com 3 ou mais caracteres
        assertThatCode(() -> restauranteValidator.validateNomeTamanho("ABC"))
                .doesNotThrowAnyException();
    }



    @Test
    void deveLancarExcecao_SeTipoCozinhaForNulo() {
        // Testando tipo de cozinha nulo
        assertThatThrownBy(() -> restauranteValidator.validateTipoCozinha(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("O tipo de cozinha não pode ser nulo.");
    }

    @Test
    void naoDeveLancarExcecao_SeTipoCozinhaForValido() {
        // Testando tipo de cozinha válido
        assertThatCode(() -> restauranteValidator.validateTipoCozinha(TiposCozinhaEnum.ITALIANA))
                .doesNotThrowAnyException();
    }






}