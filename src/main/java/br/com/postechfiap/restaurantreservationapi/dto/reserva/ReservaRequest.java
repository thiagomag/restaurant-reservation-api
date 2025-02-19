package br.com.postechfiap.restaurantreservationapi.dto.reserva;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ReservaRequest {

    @Schema(description = "ID do usuário que está fazendo a reserva", example = "123")
    private Long usuarioId;

    @Schema(description = "ID do restaurante onde a reserva será feita", example = "1")
    private Long restauranteId;

    @Schema(description = "Data e hora da reserva", example = "2024-03-15T19:30:00")
    private LocalDateTime dataHoraReserva;

    @Schema(description = "Número de pessoas para a reserva", example = "4")
    private int numeroDePessoas;
}
