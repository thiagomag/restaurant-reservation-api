package br.com.postechfiap.restaurantreservationapi.dto.restaurante.busca;


import br.com.postechfiap.restaurantreservationapi.enuns.TiposCozinhaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class RestauranteBuscaTipoCozinhaRequest {

    @NotNull(message = "O tipo de cozinha não pode ser nulo.")
    @Schema(description = "Tipo de cozinha do restaurante.", example = "ITALIANA")
    private TiposCozinhaEnum tipoCozinha;
}

