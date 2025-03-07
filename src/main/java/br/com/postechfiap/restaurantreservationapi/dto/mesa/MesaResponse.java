package br.com.postechfiap.restaurantreservationapi.dto.mesa;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesaResponse {

    @Schema(description = "Identificador único da mesa", example = "001-001")
    private String id;

    @Schema(description = "Número da mesa no restaurante", example = "10")
    private Integer numeroMesa;

    @Schema(description = "ID do restaurante ao qual a mesa pertence", example = "1")
    private Long restauranteId;
}
