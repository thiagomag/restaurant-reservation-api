package br.com.postechfiap.restaurantreservationapi.dto.reserva;


import br.com.postechfiap.restaurantreservationapi.entities.Reserva;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReservaResponse {

    private Long reservaId;
    private String mesaId;
    private LocalDateTime dataHoraReserva;
    private int numeroDePessoas;

    public static ReservaResponse toDto(Reserva reserva) {
        return ReservaResponse.builder()
                .reservaId(reserva.getId())
                .mesaId(reserva.getMesa().getId())
                .dataHoraReserva(reserva.getDataHoraReserva())
                .numeroDePessoas(reserva.getNumeroDePessoas())
                .build();
    }

    public static List<ReservaResponse> toList(List<Reserva> reservas) {
        return reservas.stream().map(ReservaResponse::toDto).collect(Collectors.toList());
    }
}
