package br.com.postechfiap.restaurantreservationapi.dto.reserva;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import java.time.LocalDateTime;


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

    private Long id; // ID da reserva
    private Long usuarioId; // ID do usuário que fez a reserva
    private Long mesaId; // ID da mesa
    private LocalDateTime dataHoraReserva; // Data e hora da reserva
}