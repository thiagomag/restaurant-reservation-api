package br.com.postechfiap.restaurantreservationapi.dto.reserva.gerenciamento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ReservaAtualizarDataHoraRequest {

    @NotNull
    @Schema(description = "ID da Reserva que está sendo cancelada", example = "5")
    private Long reservaId;

    @NotNull(message = "Data e hora não pode ser nulo.")
    @Schema(description = "Data e Hora desejada da alteração", example = "2025-02-24T15:30:00")
    private LocalDateTime dataAlteracao;
}


