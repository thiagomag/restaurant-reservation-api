package br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class RestauranteBuscaNomeRequest {

    @NotBlank(message = "O nome do restaurante é obrigatório")
    @Schema(description = "Nome do restaurante", example = "Restaurante do Chef", required = true)
    private String nome;
}
