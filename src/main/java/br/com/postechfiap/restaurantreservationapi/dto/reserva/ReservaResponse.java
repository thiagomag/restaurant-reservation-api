package br.com.postechfiap.restaurantreservationapi.dto.reserva;


import br.com.postechfiap.restaurantreservationapi.entities.Mesa;
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
    private Long usuarioId;
    private String  restauranteName;
    private List<String> mesas;  // Agora, temos uma lista de IDs de mesas
    private LocalDateTime dataHoraReserva;
    private int numeroDePessoas;

    // Converte uma única reserva para DTO, incluindo as mesas associadas
    public static ReservaResponse toDto(Reserva reserva) {
        return ReservaResponse.builder()
                .reservaId(reserva.getId())
                .mesas(reserva.getMesas().stream().map(Mesa::getId).collect(Collectors.toList())) // Lista de IDs de mesas
                .dataHoraReserva(reserva.getDataHoraReserva())
                .numeroDePessoas(reserva.getNumeroDePessoas())
                .build();
    }

    // Converte uma lista de reservas para a lista de DTOs
    public static List<ReservaResponse> toList(List<Reserva> reservas) {
        return reservas.stream().map(ReservaResponse::toDto).collect(Collectors.toList());
    }
}