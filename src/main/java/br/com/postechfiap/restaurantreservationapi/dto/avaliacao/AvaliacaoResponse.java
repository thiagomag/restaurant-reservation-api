package br.com.postechfiap.restaurantreservationapi.dto.avaliacao;


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
@Schema(description = "Resposta de avaliação de reserva")
public class AvaliacaoResponse {

    @Schema(description = "ID da avaliação", example = "1")
    private Long id;

    @Schema(description = "ID da reserva associada à avaliação", example = "123")
    private Long reservaId;

    @Schema(description = "ID do restaurante avaliado", example = "456")
    private Long restauranteId;

    @Schema(description = "Nota da avaliação (de 1 a 5)", example = "4")
    private Integer nota;

    @Schema(description = "Comentário sobre a experiência", example = "Excelente comida, muito saborosa!")
    private String comentario;
}