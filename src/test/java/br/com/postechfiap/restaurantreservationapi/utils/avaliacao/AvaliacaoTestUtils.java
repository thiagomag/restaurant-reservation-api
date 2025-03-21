package br.com.postechfiap.restaurantreservationapi.utils.avaliacao;

import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoRequest;
import br.com.postechfiap.restaurantreservationapi.dto.avaliacao.AvaliacaoResponse;
import br.com.postechfiap.restaurantreservationapi.entities.Avaliacao;
import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import br.com.postechfiap.restaurantreservationapi.enuns.NotaEnum;

public class AvaliacaoTestUtils {

    public static Avaliacao buildAvaliacao() {
        final var avaliacao = new Avaliacao();
        avaliacao.setId(1L);
        avaliacao.setNota(NotaEnum.EXCELENTE);
        avaliacao.setReserva(Reserva.builder()
                .id(1L)
                .build());
        avaliacao.setComentario("Excelente restaurante");
        return avaliacao;
    }

    public static AvaliacaoResponse buildAvaliacaoResponse() {
        return AvaliacaoResponse.builder()
                .id(1L)
                .nota(5)
                .reservaId(1L)
                .comentario("Excelente restaurante")
                .build();
    }

    public static AvaliacaoRequest buildAvaliacaoRequest() {
        return AvaliacaoRequest.builder()
                .nota(5)
                .reservaId(1L)
                .comentario("Excelente restaurante")
                .build();
    }
}
