package br.com.postechfiap.restaurantreservationapi.validator;

import br.com.postechfiap.restaurantreservationapi.exception.reserva.ReservaJaAvaliadaException;
import br.com.postechfiap.restaurantreservationapi.interfaces.avaliacao.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliacaoValidator {

    private final AvaliacaoRepository avaliacaoRepository;

    public void validarNota(Integer nota) {
        if (nota == null || nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve ser entre 1 e 5.");
        }
    }

    public void validarSeReservaJaFoiAvaliada(Long reservaId) {
        boolean exists = avaliacaoRepository.existsByReservaId(reservaId);
        if (exists) {
            throw new ReservaJaAvaliadaException(reservaId);
        }
    }
}

