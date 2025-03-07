package br.com.postechfiap.restaurantreservationapi.dto.reserva;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "O ID do usuário não pode ser nulo.")  // Validação de não nulo
    private Long usuarioId;

    @Schema(description = "ID do restaurante onde a reserva será feita", example = "1")
    @NotNull(message = "O ID do restaurante não pode ser nulo.")  // Validação de não nulo
    private Long restauranteId;

    @Schema(description = "Data e hora da reserva", example = "2024-03-15T19:30:00")
    @NotNull(message = "A data e hora da reserva não podem ser nulas.")  // Validação de não nulo
    private LocalDateTime dataHoraReserva;

    @Schema(description = "Número de pessoas para a reserva", example = "4")
    @Min(value = 1, message = "O número de pessoas deve ser no mínimo 1.")  // Valor mínimo
    @Max(value = 99, message = "O número de pessoas não pode ser superior a 99.")  // Valor máximo
    private int numeroDePessoas;
}
