package br.com.postechfiap.restaurantreservationapi.dto.mesa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MesaRequest {

    @Schema(description = "ID do restaurante onde as mesas serão adicionadas", example = "1")
    private Long restauranteId;

    @Schema(description = "Quantidade de mesas a serem criadas", example = "10")
    private Integer quantidadeMesas;
}
