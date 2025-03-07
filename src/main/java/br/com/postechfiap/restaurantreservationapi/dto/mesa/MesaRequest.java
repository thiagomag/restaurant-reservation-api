package br.com.postechfiap.restaurantreservationapi.dto.mesa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MesaRequest {

    @NotNull(message = "O ID do restaurante não pode ser nulo")
    @Schema(description = "ID do restaurante onde as mesas serão adicionadas", example = "1")
    private Long restauranteId;

    @Min(value = 1, message = "A quantidade de mesas não pode ser negativa")
    @Schema(description = "Quantidade de mesas a serem criadas", example = "10")
    private Integer quantidadeMesas;
}
