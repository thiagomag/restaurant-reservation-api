package br.com.postechfiap.restaurantreservationapi.validator;


import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteValidator {

    // Valida se o nome do restaurante não é nulo ou vazio
    public void validateNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do restaurante não pode ser vazio ou nulo");
        }
    }

    // Valida o tamanho do nome (exemplo: no mínimo 3 caracteres)
    public void validateNomeTamanho(String nome) {
        if (nome.length() < 3) {
            throw new IllegalArgumentException("O nome do restaurante deve ter pelo menos 3 caracteres");
        }
    }


    // Valida se o tipo de cozinha não é nulo
    public void validateTipoCozinha(TiposCozinhaEnum tipoCozinha) {
        if (tipoCozinha == null) {
            throw new IllegalArgumentException("O tipo de cozinha não pode ser nulo.");
        }
    }


}
